from . import models

from rest_framework import serializers
from models import *
from datetime import datetime

class ProductSerializer(serializers.ModelSerializer):

    id = serializers.IntegerField(read_only=True, source='productid')
    code = serializers.CharField(source='productcode')
    description = serializers.CharField(source='productdescription')
    price = serializers.FloatField(source='buyprice')
    categoryname = serializers.CharField(source='categoryid.categoryname')
    categoryid = serializers.CharField(source='categoryid.categoryid', required = False)
    qty = serializers.IntegerField(source='quantityinstock',required=False)
    class Meta:
        model = Product
        fields = ('id', 'code', 'description','price','categoryname','categoryid','qty')

    def create(self, validated_data):
        print validated_data
        productObj = Product.objects.create(
            buyprice = validated_data['buyprice'],
            productcode = validated_data.get('productcode',None),
            productdescription = validated_data.get('productdescription', None)
        )
        categoryObj, created = Category.objects.get_or_create(
            categoryname=validated_data['categoryid'].get('categoryname')
        )
        productObj.categoryid = categoryObj
        categoryObj.save()
        productObj.save()
        return productObj


    def partial_update(self, instance, validated_data):

        print validated_data
        instance.buyprice = validated_data.get('buyprice',instance.buyprice)
        instance.productcode = validated_data.get('productcode',instance.productcode)
        instance.productdescription = validated_data.get('productdescription',instance.productdescription)

        categoryObj, created = Category.objects.get_or_create(
            categoryname=instance.categoryid.categoryname
        )

        instance.categoryid = categoryObj
        categoryObj.categoryname = validated_data['categoryid'].get('categoryname')
        print categoryObj.categoryname
        print categoryObj.categoryid
        categoryObj.save()
        instance.save()
        return instance


    def update(self, instance, validated_data):
        if self.partial == True:
             return self.partial_update(instance, validated_data)

        print validated_data
        instance.buyprice = validated_data.get('buyprice')
        instance.productcode = validated_data.get('productcode')
        instance.productdescription = validated_data.get('productdescription')
        categoryObj, created = Category.objects.get_or_create(
            categoryname = validated_data['categoryid'].get('categoryname')
        )
        instance.categoryid = categoryObj
        print categoryObj.categoryname
        print categoryObj.categoryid
        categoryObj.save()
        instance.save()
        return instance


class UsersSerializer(serializers.ModelSerializer):
    class Meta:
        model = Users


class CategorySerializer(serializers.ModelSerializer):
    class Meta:
        model = Category



class OrderLineSerializer(serializers.ModelSerializer):
    order_id = serializers.IntegerField(source='orderid.orderid',required=False, read_only=True)
    id = serializers.IntegerField(required=False, read_only=True)
    product_id = serializers.IntegerField(source='productid.productid')
    code = serializers.CharField(source='productid.productcode')
    qty = serializers.IntegerField(source='quantityordered')
    price = serializers.FloatField(source='priceeach', required = True)
    class Meta:
        model = OrderlineCopy
        fields = ('product_id','price','order_id','id','code','qty')




#TODO remove page size

class OrdersSerializer(serializers.ModelSerializer):
    username = serializers.CharField(source='userid.customername', required = False)
    address = serializers.CharField(source='userid.addressline1', required = False)
    id = serializers.IntegerField(read_only=True, source='orderid')
    status = serializers.CharField(max_length=255, required=False)

    class Meta:
        model = Orders
        fields = ('id','status','username','address')

    def create(self, validated_data):
        print validated_data

        ordersObj = Orders.objects.create(
            status=validated_data.get('status', "CREATED"),
            orderdate=datetime.now(),
        )

        if 'userid' in validated_data.keys():
            print  "User name is given"
            usersObj, created = Users.objects.get_or_create(
                customername = validated_data['userid']['customername']
            )
            usersObj.addressline1 = validated_data['userid'].get('addressline1', None)
            ordersObj.userid = usersObj
            usersObj.save()
            ordersObj.save()

        return ordersObj


    def partial_update(self, instance, validated_data):

        instance.status = validated_data.get('status', instance.status)

        if instance.userid == None and 'userid' not in validated_data.keys():
            instance.save()
            return instance

        if instance.userid != None and 'userid' not in validated_data.keys():
            instance.save()
            return instance
        # Now validate data has user id


        if instance.userid != None:
            usersObj, created = Users.objects.update_or_create(
                userid=instance.userid.userid,
                defaults={'customername': validated_data['userid'].get('customername', instance.userid.customername),
                          'addressline1': validated_data['userid'].get('addressline1', instance.userid.addressline1)
                          }
            )
            instance.userid = usersObj
            usersObj.save()
            instance.save()
            return instance
        # If instance does not userid then create

        usersObj, created = Users.objects.get_or_create(
            customername=validated_data['userid']['customername']
        )
        usersObj.addressline1 = validated_data['userid'].get('addressline1', None)
        instance.userid = usersObj
        usersObj.save()
        instance.save()
        return instance


    def update(self, instance, validated_data):
        if self.partial == True:
            return self.partial_update(instance, validated_data)


        instance.status = validated_data.get('status', None)

        if instance.userid == None and 'userid' not in validated_data.keys():
            instance.save()
            return instance

        if instance.userid != None and 'userid' not in validated_data.keys():
            instance.userid = None
            instance.save()
            return instance

        if instance.userid == None:
            usersObj, created = Users.objects.get_or_create(
                customername = validated_data['userid']['customername']
            )
            usersObj.addressline1 = validated_data['userid'].get('addressline1',None)
            instance.userid = usersObj
            usersObj.save()
        else:
            usersObj, created = Users.objects.update_or_create(
                customername=instance.userid.customername,
                defaults = {'customername':validated_data['userid'].get('customername', None),
                            'addressline1': validated_data['userid'].get('addressline1', None)
                          }
            )
            instance.userid = usersObj
            usersObj.save()
        instance.save()
        return instance


