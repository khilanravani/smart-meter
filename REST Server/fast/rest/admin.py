from django.contrib import admin
from rest.models import Profile, Record, Bill
# Register your models here.
admin.site.register(Profile)
admin.site.register(Bill)
admin.site.register(Record)
