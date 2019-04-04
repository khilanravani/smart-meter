from django.urls import include, path
from django.conf.urls import url
from .views import UserCreate, ProfileAPIView, ProfileCreate, LoginView,  Logout, ProfileList, MeterManagerView, MeterUserView, MeterManagerView, BranchView


urlpatterns = [
    url(r'^logout/', Logout.as_view()),
    path("user/<str:username>/", ProfileAPIView.as_view(),
         name="meter user_details"),
    path("user/", ProfileList.as_view(), name="meter profile_list"),
    path("user/signup/", UserCreate.as_view(), name="meter user_create"),
    path("user/signup/profile/", ProfileCreate.as_view(),
         name="meter profile_create"),
    path("user/profile/", MeterUserView.as_view(), name="meter user create"),
    path("user/login/", LoginView.as_view(), name="meter user login"),

    path("manager/", ProfileList.as_view(), name="manager_profile_list"),
    path("manager/signup/", UserCreate.as_view(), name="manager_create"),
    path("manager/signup/profile/", ProfileCreate.as_view(),
         name="manager_profile_create"),
    path("user/profile/", MeterManagerView.as_view(), name="manager_profile"),
    path("manager/login/", LoginView.as_view(), name="manager_login")
]
