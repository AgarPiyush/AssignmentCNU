from django.shortcuts import render

# Create your views here.
from . import models
from datetime import datetime
from models import *
from django.db.models import Count, Sum, F, ExpressionWrapper
from django.db import models
from django.http import JsonResponse
from rest_framework import viewsets
from serializers import *


from django.http import HttpResponse
def index(request):
    return HttpResponse("Hello, world. You're at the polls index.")

def categories(request):
    return HttpResponse(models.Category.objects.all())

def orders(request):
    return HttpResponse(models.Orders.objects.all())

def ordersDetails(request):
    startTime = request.GET.get('startDate','01/01/1900')
    startTime = datetime.strptime(startTime,'%m/%d/%Y').strftime('%Y-%m-%d')
    endTime = request.GET.get('endDate','01/01/2050')
    endTime = datetime.strptime(endTime, '%m/%d/%Y').strftime('%Y-%m-%d')
    print startTime
    print endTime

    objs = OrderlineCopy.objects.filter(orderid__orderdate__gte = startTime, orderid__orderdate__lte = endTime).values(
        'orderid__orderdate').annotate(orders = Count('orderid__orderid'), qty = Sum('quantityordered'),
                                       sale_price = ExpressionWrapper(Sum(F('priceeach') * F('quantityordered')), output_field = models.FloatField()),
                                       buy_price = ExpressionWrapper(Sum(F('quantityordered')*F('productid__buyprice')),  output_field = models.FloatField()),
                                       profit = ExpressionWrapper(Sum(F('priceeach')*F('quantityordered')) - Sum(F('quantityordered')*F('productid__buyprice')), output_field = models.FloatField())).order_by('-orderid__orderdate')
    # for each in objs
  # ExpressionWrapper(Count('orderid__orderid'), output_field=FloatField()
  #   for each in objs:
  #         print each
    dicts = []
    for each in objs:
        dict_obj = dict()
        dict_obj['date'] = datetime.strptime(str(each["orderid__orderdate"]),'%Y-%m-%d').strftime('%m/%d/%Y')
        dict_obj['orders'] = each["orders"]
        dict_obj['profit'] = each["profit"]
        dict_obj['buy_price'] = each["buy_price"]
        dict_obj['sale_price'] = each["sale_price"]
        dict_obj['qty'] = each["qty"]
        dicts.append(dict_obj)

    dict2 = dict()
    dict2["data"] = dicts

    return JsonResponse(dict2)


class ProductViewSet(viewsets.ModelViewSet):

    serializer_class = ProductSerializer
    def get_queryset(self):
        return Product.objects.all()



class OrdersViewSet(viewsets.ModelViewSet):

    serializer_class = OrdersSerializer
    def get_queryset(self):
        return Orders.objects.all()


class UserssViewSet(viewsets.ModelViewSet):

    serializer_class = UsersSerializer
    def get_queryset(self):
        return Orders.objects.all()

