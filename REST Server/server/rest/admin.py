from django.contrib import admin
from rest.models import Profile, MeterUser, MeterManager, Branch, Records
# Register your models here.
admin.site.register(Profile)
admin.site.register(MeterManager)
admin.site.register(MeterUser)
admin.site.register(Branch)
admin.site.register(Records)
