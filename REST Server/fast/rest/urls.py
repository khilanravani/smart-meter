
from django.urls import include, path
from django.conf.urls import url
from .views import ProfileAPIView, UserCreate, LoginView, ProfileList, RecordListView, BillListView, Logout, ProfileCreate

urlpatterns = [
    url(r'^logout/', Logout.as_view()),
    path("user/<str:username>/", ProfileAPIView.as_view(), name="user_details"),
    path("user/", ProfileList.as_view(), name="profile_list"),
    path("user/signup/", UserCreate.as_view(), name="user_create"),
    path("user/signup/profile/", ProfileCreate.as_view(), name="profile_create"),
    path("user/login/", LoginView.as_view(), name="login"),
    path("user/record/<str:username>/", RecordListView.as_view(), name="record"),
    path("user/bill/<str:username>/", BillListView.as_view(), name="bill")
]
