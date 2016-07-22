from django.conf.urls import include, url, patterns
from django.contrib import admin
from rest_framework import routers
from . import views
from views import *


router = routers.DefaultRouter()
router.register(r'products',ProductViewSet, base_name='ProductViewSet')
router.register(r'orders',OrdersViewSet, base_name='OrdersViewSet')


urlpatterns = [
    url(r'^', include(router.urls)),
   # url(r'^reports/daily-sale$', views.ordersDetails, name='ordersDetails'),
  #  url(r'^docs/', include('rest_framework_swagger.urls')),
]
