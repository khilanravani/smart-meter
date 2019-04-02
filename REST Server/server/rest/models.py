from django.db import models
from django.contrib.auth.models import User


class Profile(models.Model):
    user = models.OneToOneField(User, on_delete=models.CASCADE)
    bio = models.TextField(max_length=500, blank=True)
    address = models.CharField(max_length=50, blank=True)
    birth_date = models.DateField(null=True, blank=True)

    def __str__(self):
        return self.user.__str__()


class MeterUser(models.Model):
    profile = models.OneToOneField(Profile, on_delete=models.CASCADE)
    contact_number = models.CharField(max_length=13)
    meter_id = models.CharField(max_length=50)

    class Meta:
        unique_together = ('meter_id', 'profile', 'contact_number')

    def __str__(self):
        return self.profile.__str__()


class MeterManager(models.Model):
    profile = models.OneToOneField(Profile, on_delete=models.CASCADE)
    contact_number = models.CharField(max_length=13)
    group_num = models.IntegerField()

    class Meta:
        unique_together = ('group_num', 'profile', 'contact_number')

    def __str__(self):
        return self.profile.__str__()


class Branch(models.Model):
    user = models.ForeignKey(MeterUser, on_delete=models.CASCADE)
    manager = models.ForeignKey(MeterManager, on_delete=models.CASCADE)

    class Meta:
        unique_together = ('user', 'manager')

    def __str__(self):
        return self.user.__str__() + " " + self.manager.__str__()


class Records(models.Model):
    profile = models.ForeignKey('MeterUser', on_delete=models.CASCADE)
    timestamp = models.TimeField(auto_now=False, auto_now_add=True)
    volt = models.FloatField()
    current = models.FloatField()
    watt = models.FloatField()
    power = models.FloatField()

    class Meta:
        unique_together = ('profile', 'timestamp')

    def __str__(self):
        return self.profile.__str__() + " " + str(self.timestamp)
