
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
from .models import Profile, MeterManager, MeterUser, Records, Branch
from rest_framework.authtoken.models import Token
from .serializers import UserSerializer, ProfileSerializer, MeterManagerSerializer, MeterUserSerializer, RecordsSerializer
from django.core import serializers


# Create your views here.

def get_profile_id(username):
    return get_object_or_404(Profile, user=get_object_or_404(User, username=username)).id


class UserCreate(generics.CreateAPIView):
    authentication_classes = ()
    permission_classes = ()
    serializer_class = UserSerializer


class ProfileCreate(generics.CreateAPIView):
    authentication_classes = ()
    permission_classes = ()
    serializer_class = ProfileSerializer


class ProfileAPIView(APIView):
    permission_classes = (IsAuthenticated,)

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
    permission_classes = (IsAuthenticated,)


class MeterUserView(APIView):
    def get(self, meter_id):
        meter_user = get_object_or_404(MeterUser, meter_id=meter_id)
        serializer = MeterUserSerializer(meter_user)
        return Response(serializer.data, status=200)


# class MeterUserListView(APIView):
#     def get(self, group_num):
#         manager = get_object_or_404(MeterManager, group_num=group_num)
#         branch = get_list_or_404(Branch, manager=manager)
#         meter_users =
#         return


class MeterManagerView(APIView):
    def get(self, group_num):
        meter_man = get_object_or_404(MeterUser, group_num=group_num)
        serializer = MeterManagerSerializer(meter_man)
        return Response(serializer.data, status=200)
