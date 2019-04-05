from django.shortcuts import render
from rest_framework.views import APIView
from django.core.exceptions import PermissionDenied
from rest_framework.response import Response
from rest_framework import status
from rest_framework import generics
from django.contrib.auth.models import User
from rest_framework.permissions import IsAuthenticated, AllowAny
from django.shortcuts import get_object_or_404, get_list_or_404
from django.contrib.auth import authenticate
from .models import Profile, Record, Bill
from rest_framework.authtoken.models import Token
from .serializers import UserSerializer, ProfileSerializer, RecordsSerializer, BillSerializer
from rest_framework.authtoken.models import Token
from django.core import serializers, exceptions
from django.utils.timezone import utc
import copy
from django.utils import timezone
# Create your views here.


def save_in_bill(request):
    request.data['cost'] = request.data['energy'] * 14
    serializer = BillSerializer(data=request.data)
    if serializer.is_valid():
        serializer.save()
    else:
        raise exceptions.ValidationError(message=serializer.errors)


class UserCreate(generics.CreateAPIView):
    authentication_classes = ()
    permission_classes = ()
    serializer_class = UserSerializer


class ProfileCreate(generics.CreateAPIView):
    authentication_classes = ()
    permission_classes = ()
    serializer_class = ProfileSerializer


class ProfileAPIView(APIView):
    permission_classes = (AllowAny,)

    def get(self, request, username):
        user = get_object_or_404(User, username=username)
        user_profile = get_object_or_404(Profile, user=user)
        data = ProfileSerializer(user_profile).data
        return Response(data)

    def put(self, request, username):
        user = get_object_or_404(User, username=username)
        user_profile = get_object_or_404(Profile, user=user)
        serialize = ProfileSerializer(user_profile, data=request.data)
        if serialize.is_valid():
            serialize.save()
            Response(status=200)
        return Response(status=404)

    def delete(self, request, username):
        user = get_object_or_404(User, username=username)
        user_profile = get_object_or_404(Profile, user=user)
        user_profile.delete()
        user.delete()
        return Response(status=200)


class LoginView(generics.GenericAPIView):
    permission_classes = (AllowAny,)
    serializer_class = UserSerializer

    def post(self, request, format=None):
        username = request.data.get("username")
        password = request.data.get("password")
        user = authenticate(username=username, password=password)
        if user is not None:
            return Response({"token": user.auth_token.key})
        else:
            return Response({"error": "Wrong Credentials"}, status=401)


class Logout(APIView):
    def get(self, request, format=None):
        # simply delete the token to force a login
        request.user.auth_token.delete()
        return Response(status=status.HTTP_200_OK)


class ProfileList(generics.ListAPIView):
    queryset = Profile.objects.all()
    serializer_class = ProfileSerializer
    permission_classes = ()


class RecordListView(APIView):
    permission_classes = (AllowAny,)

    def get(self, request, username):
        user = get_object_or_404(User, username=username)
        profile = get_object_or_404(Profile, user=user)
        records = get_list_or_404(Record, profile=profile)
        serialize = RecordsSerializer(records, many=True)
        return Response(serialize.data, status=200)

    def post(self, request, username):
        user = get_object_or_404(User, username=username)
        profile = get_object_or_404(Profile, user=user)
        request.data['profile'] = profile.id
        dt = timezone.now()
        request.data['time'] = dt.strftime('%Y-%m-%d %H:%M:%S')
        try:
            if(request.data['bill_time'] == 1):
                save_in_bill(request)
            request.data.pop('bill_time')
            serialize = RecordsSerializer(data=request.data)
            if serialize.is_valid():
                serialize.save()
                return Response(serialize.data, status=200)
        except exceptions.ValidationError as err:
            return Response(err, status=400)
        return Response(serialize.errors, status=400)


class BillListView(APIView):
    permission_classes = ()

    def get(self, request, username):
        user = get_object_or_404(User, username=username)
        profile = get_object_or_404(Profile, user=user)
        bills = get_list_or_404(Bill, profile=profile)
        serialize = BillSerializer(bills, many=True)
        return Response(serialize.data, status=200)


class BillAPIView(APIView):
    def get(self, request, username, billid):
        bill = get_object_or_404(Bill, id=billid)
        serialize = BillSerializer(bill)
        return Response(serialize.data, status=200)

    def put(self, request, username, billid):
        bill = get_object_or_404(Bill, id=billid)
        serialize = BillSerializer(bill, data=request.data)
        if serialize.is_valid():
            serialize.save()
            Response(status=200)
        return Response(status=404)
