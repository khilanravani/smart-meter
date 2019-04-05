from rest_framework import serializers
from .models import Profile, Record, Bill
from django.contrib.auth.models import User


class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ('username', 'first_name', 'last_name', 'email')


class ProfileSerializer(serializers.ModelSerializer):
    """
    A student serializer to return the student details
    """
    user = UserSerializer(required=True)

    class Meta:
        model = Profile
        fields = ('user', 'contact_number', 'meter_id')

    def create(self, validated_data):
        """
        Overriding the default create method of the Model serializer.
        :param validated_data: data containing all the details of student
        :return: returns a successfully created student record
        """
        user_data = validated_data.pop('user')
        user = UserSerializer.create(
            UserSerializer(), validated_data=user_data)
        student, created = Profile.objects.update_or_create(
            user=user, contact_number=validated_data.pop('contact_number'), meter_id=validated_data.pop('meter_id'))
        return student


class RecordsSerializer(serializers.ModelSerializer):
    class Meta:
        model = Record
        fields = '__all__'


class BillSerializer(serializers.ModelSerializer):
    class Meta:
        model = Bill
        fields = '__all__'
