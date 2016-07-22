from . import models

from rest_framework import serializers
from models import *
class ProductSerializer(serializers.ModelSerializer):
    class Meta:
        model = Product
        fields = ('productid', 'productcode', 'productdescription','buyprice','categoryid')


class UsersSerializer(serializers.ModelSerializer):
    class Meta:
        model = Users


class OrdersSerializer(serializers.ModelSerializer):
#    user = UsersSerializer()
    class Meta:
        model = Orders
        depth = 1

