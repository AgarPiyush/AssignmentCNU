from django.shortcuts import render
from rest_framework.decorators import detail_route
from rest_framework import mixins
from rest_framework import generics

# Create your views here.

from django.db.models import Count, Sum, F, ExpressionWrapper
from django.http import JsonResponse, HttpResponse
from rest_framework import viewsets
from serializers import *
from rest_framework import mixins

class LogMiddleware( object ):

    def process_response(self, request, response):
        if request.__dict__['path'].startswith("/api"):
            response._container = ['{"data":' + response._container[0] + "}"]
        return response

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
    return JsonResponse(dicts, safe=False)


class ProductViewSet(viewsets.ModelViewSet):

    serializer_class = ProductSerializer
    def get_queryset(self):
        return Product.objects.all().filter(discontinued=0)

    def destroy(self, request, *args, **kwargs):
        productObj = self.get_object()
        productObj.discontinued = 1
        productObj.save()
        serializer = ProductSerializer(productObj)
        return JsonResponse(serializer.data, safe=False)



class OrdersViewSet(viewsets.ModelViewSet):

    serializer_class = OrdersSerializer
    def get_queryset(self):
        return Orders.objects.all().filter(discontinued=0)

    def destroy(self, request, *args, **kwargs):
        ordersObj = self.get_object()
        ordersObj.discontinued = 1
        ordersObj.save()
        serializer = OrdersSerializer(ordersObj)
        return JsonResponse(serializer.data, safe=False)



class UserssViewSet(viewsets.ModelViewSet):
    serializer_class = UsersSerializer
    def get_queryset(self):
        return Orders.objects.all()

class CategoryViewSet(viewsets.ModelViewSet):
    serializer_class = CategorySerializer
    def get_queryset(self):
        return Category.objects.all()



class OrderLineViewSet(mixins.CreateModelMixin, mixins.ListModelMixin, mixins.RetrieveModelMixin,mixins.DestroyModelMixin, viewsets.GenericViewSet):
    serializer_class = OrderLineSerializer
    orderid = serializers.IntegerField(source='orderid.orderid')

    print "Inside orderlineview"
    def get_queryset(self):
        return OrderlineCopy.objects.all()

    def list(self, request, *args, **kwargs):
        queryset = OrderlineCopy.objects.filter(orderid=kwargs["order_id"])
        serializer = OrderLineSerializer(queryset, many=True)
        return JsonResponse(serializer.data, safe = False)

    def retrieve(self, request, *args, **kwargs):
        queryset = OrderlineCopy.objects.filter(id=kwargs["pk"], orderid = kwargs["order_id"])
        serializer = OrderLineSerializer(queryset,many=True)
        return JsonResponse(serializer.data, safe=False)

    #
    def create(self, request, *args, **kwargs):

        productObj = Product.objects.filter(productid = request.data["product_id"])
        ordersObj = Orders.objects.filter(orderid = kwargs["order_id"])
        print request.data
        orderlineObj = OrderlineCopy.objects.create(
            orderid = ordersObj[0],
            productid = productObj[0],
            priceeach = request.data["price"],
       #     quantityordered = request.data["qty"]
        )
        serializer = OrderLineSerializer(orderlineObj)
        return JsonResponse(serializer.data, safe=False)

class ProductCategoryViewSet(mixins.ListModelMixin, viewsets.GenericViewSet):
    def list(self, request, *args, **kwargs):
        groupBy = request.GET.get('group_by', None)
        productCode = request.GET.get('code', None)
        categoryName = request.GET.get('category__name', None)
        query = Product.objects.filter(discontinued=0)
        if productCode:
            query = query.filter(productcode = productCode)
        if categoryName:
            query = query.filter(categoryid__categoryname = categoryName)

        if groupBy:
            query = query.values('categoryid__categoryname')
            query = query.annotate(count=Count('productid'))
            query = query.annotate(category_id=F('categoryid__categoryid'))
            lists = [query]
            return JsonResponse(lists,safe=False)
        else:
            query = query.aggregate(count=Count('productcode'))
            query = [query]
            return JsonResponse(query,safe=False)



class OrderCategoryViewSet(mixins.ListModelMixin, viewsets.GenericViewSet):
    def list(self, request, *args, **kwargs):
        groupBy = request.GET.get('group_by', None)
        productCode = request.GET.get('code', None)
        categoryName = request.GET.get('category__name', None)
        query = OrderlineCopy.objects.all()
        print categoryName
        if productCode:
            query = query.filter(productid__productcode = productCode)

        if categoryName:
             query = query.filter(productid__categoryid__categoryname = categoryName)

        if groupBy:
            if groupBy == 'category__name':
                query = query.values('productid__categoryid__categoryname')
                query = query.annotate(count=Count('orderid'))
                query = query.annotate(category_id=F('productid__categoryid__categoryid'))
                lists = [query]
                return JsonResponse(lists,safe=False)
            else:
                query = query.values('productid__productcode')
                query = query.annotate(count=Count('orderid'))
                query = query.annotate(product_id=F('productid__productid'))
                lists = [query]
                return JsonResponse(lists, safe=False)
        else:
            query = query.aggregate(count=Count('orderid'))
            query = [query]
            return JsonResponse(query,safe=False)


class ProductsInCategoryViewSet(mixins.ListModelMixin, viewsets.GenericViewSet):
    def list(self, request, *args, **kwargs):
        query = Product.objects.filter(discontinued=0)
        query = query.filter(categoryid__categoryid = kwargs["category_id"])
        serializer = ProductSerializer(query, many=True)
        return JsonResponse(serializer.data, safe=False)

class HealthViewSet(mixins.ListModelMixin, viewsets.GenericViewSet):
    def list(self, request, *args, **kwargs):
        query = Product.objects.filter(discontinued=0)
        query = query.filter(categoryid__categoryid = kwargs["category_id"])
        serializer = ProductSerializer(query, many=True)
        return JsonResponse(serializer.data, safe=False)

def health(request):
    return JsonResponse(status=200)
