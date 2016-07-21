from django.conf.urls import include, url, patterns
from django.contrib import admin
from rest_framework import routers
from . import views
from views import *


router = routers.DefaultRouter()
router.register(r'products/summary', ProductCategoryViewSet, base_name='ProductCategoryViewSet')
router.register(r'orders/summary', OrderCategoryViewSet, base_name='OrderCategoryViewSet')
router.register(r'category_products/(?P<category_id>[0-9]+)',ProductsInCategoryViewSet, base_name='ProductsInCategoryViewSet')
router.register(r'products',ProductViewSet, base_name='ProductViewSet')
router.register(r'orders',OrdersViewSet, base_name='OrdersViewSet')
router.register(r'category',CategoryViewSet, base_name='CategoryViewSet')
#router.register(r'health', HealthViewSet, base_name='HealthViewSet')
router.register(r'orders/(?P<order_id>[0-9]+)/orderLineItem', OrderLineViewSet, base_name='OrderLineViewSet')
urlpatterns = [
    url(r'^', include(router.urls)),
    url(r'^reports/daily-sale', views.ordersDetails, name='ordersDetails'),
    url(r'^health', views.health),
   # url(r'^health',views.health, name='health')
]
