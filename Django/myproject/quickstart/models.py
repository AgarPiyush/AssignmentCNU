# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#   * Rearrange models' order
#   * Make sure each model has one field with primary_key=True
#   * Make sure each ForeignKey has `on_delete` set to the desired behavior.
#   * Remove `managed = False` lines if you wish to allow Django to create, modify, and delete the table
# Feel free to rename the models, but don't rename db_table values or field names.
from __future__ import unicode_literals

from django.db import models


class Auditlogtest(models.Model):
    ipaddress = models.CharField(db_column='ipAddress', max_length=256, blank=True, null=True)  # Field name made lowercase.
    executetime = models.CharField(db_column='ExecuteTime', max_length=256, blank=True, null=True)  # Field name made lowercase.
    requesttime = models.CharField(db_column='RequestTime', max_length=256, blank=True, null=True)  # Field name made lowercase.
    responsecode = models.CharField(db_column='ResponseCode', max_length=256, blank=True, null=True)  # Field name made lowercase.
    requesturl = models.CharField(db_column='RequestUrl', max_length=256, blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = True
        db_table = 'AuditLogTest'


class Category(models.Model):
    categoryid = models.AutoField(db_column='categoryId', primary_key=True)  # Field name made lowercase.
    categorydescription = models.TextField(db_column='categoryDescription', blank=True, null=True)  # Field name made lowercase.
    categoryname = models.CharField(db_column='categoryName', max_length=256, blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = True
        db_table = 'Category'


class Categorytest(models.Model):
    categoryid = models.AutoField(db_column='categoryId', primary_key=True)  # Field name made lowercase.
    categorydescription = models.TextField(db_column='categoryDescription')  # Field name made lowercase.

    class Meta:
        managed = True
        db_table = 'CategoryTest'


class Feedback(models.Model):
    userid = models.IntegerField(db_column='userId', blank=True, null=True)  # Field name made lowercase.
    email = models.CharField(max_length=11, blank=True, null=True)
    orderid = models.IntegerField(db_column='orderId', blank=True, null=True)  # Field name made lowercase.
    status = models.CharField(max_length=11, blank=True, null=True)
    username = models.CharField(db_column='userName', max_length=11, blank=True, null=True)  # Field name made lowercase.
    description = models.CharField(max_length=256, blank=True, null=True)
    timestamp = models.DateField(db_column='timeStamp', blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = True
        db_table = 'Feedback'


class Orderline(models.Model):
    orderid = models.ForeignKey('Orders', models.DO_NOTHING, db_column='orderId')  # Field name made lowercase.
    quantityordered = models.IntegerField(db_column='quantityOrdered')  # Field name made lowercase.
    priceeach = models.FloatField(db_column='priceEach')  # Field name made lowercase.
    productid = models.ForeignKey('Product', models.DO_NOTHING, db_column='productId')  # Field name made lowercase.

    class Meta:
        managed = True
        db_table = 'OrderLine'


class Orderlinetest(models.Model):
    orderid = models.ForeignKey('Orders', models.DO_NOTHING, db_column='orderId')  # Field name made lowercase.
    quantityordered = models.IntegerField(db_column='quantityOrdered')  # Field name made lowercase.
    priceeach = models.FloatField(db_column='priceEach')  # Field name made lowercase.
    productid = models.ForeignKey('Product', models.DO_NOTHING, db_column='productId')  # Field name made lowercase.

    class Meta:
        managed = True
        db_table = 'OrderLineTest'


class OrderlineCopy(models.Model):
    orderid = models.ForeignKey('Orders', models.DO_NOTHING, db_column='orderId')  # Field name made lowercase.
    quantityordered = models.IntegerField(db_column='quantityOrdered', default=0)  # Field name made lowercase.
    priceeach = models.FloatField(db_column='priceEach')  # Field name made lowercase.
    productid = models.ForeignKey('Product', models.DO_NOTHING, db_column='productId')  # Field name made lowercase.
    id = models.AutoField(db_column='id', primary_key=True)

    class Meta:
        managed = True
        db_table = 'OrderLine_copy'


class Orders(models.Model):
    orderid = models.AutoField(db_column='orderId', primary_key=True)  # Field name made lowercase.
    orderdate = models.DateField(db_column='orderDate', blank=True, null=True)  # Field name made lowercase.
    userid = models.ForeignKey('Users', models.DO_NOTHING, db_column='userId', blank=True, null=True)  # Field name made lowercase.
    status = models.CharField(max_length=255, blank=True, null=True)
    discontinued = models.IntegerField(default=0)

    class Meta:
        managed = True
        db_table = 'Orders'

    # def __unicode__(self):
    #     return unicode(self.productid)


class Orderstest(models.Model):
    orderid = models.AutoField(db_column='orderId', primary_key=True)  # Field name made lowercase.
    orderdate = models.DateField(db_column='orderDate', blank=True, null=True)  # Field name made lowercase.
    userid = models.IntegerField(db_column='userId', blank=True, null=True)  # Field name made lowercase.
    status = models.CharField(max_length=255, blank=True, null=True)

    class Meta:
        managed = True
        db_table = 'OrdersTest'


class Product(models.Model):
    productid = models.AutoField(db_column='productId', primary_key=True)  # Field name made lowercase.
    productcode = models.CharField(db_column='productCode', max_length=255, blank=True, null=True)  # Field name made lowercase.
    productname = models.CharField(db_column='productName', max_length=255, blank=True, null=True)  # Field name made lowercase.
    productdescription = models.TextField(db_column='productDescription', blank=True, null=True)  # Field name made lowercase.
    quantityinstock = models.IntegerField(db_column='quantityInStock',default=0)  # Field name made lowercase.
    buyprice = models.FloatField(db_column='buyPrice')  # Field name made lowercase.
    categoryid = models.ForeignKey(Category, models.DO_NOTHING, db_column='categoryId', blank=True, null=True)  # Field name made lowercase.
    discontinued = models.IntegerField(blank=True, null=True)

    class Meta:
        managed = True
        db_table = 'Product'

    # def __unicode__(self):
    #     return unicode(self.productid)

class Producttest(models.Model):
    productid = models.AutoField(db_column='productId', primary_key=True)  # Field name made lowercase.
    productcode = models.CharField(db_column='productCode', max_length=255, blank=True, null=True)  # Field name made lowercase.
    productname = models.CharField(db_column='productName', max_length=255, blank=True, null=True)  # Field name made lowercase.
    productdescription = models.TextField(db_column='productDescription', blank=True, null=True)  # Field name made lowercase.
    quantityinstock = models.IntegerField(db_column='quantityInStock')  # Field name made lowercase.
    buyprice = models.FloatField(db_column='buyPrice')  # Field name made lowercase.
    categoryid = models.ForeignKey(Category, models.DO_NOTHING, db_column='categoryId', blank=True, null=True)  # Field name made lowercase.
    discontinued = models.IntegerField(blank=True, null=True)

    class Meta:
        managed = True
        db_table = 'ProductTest'


class Users(models.Model):
    userid = models.AutoField(db_column='userId', primary_key=True)  # Field name made lowercase.
    customername = models.CharField(db_column='customerName', max_length=255, unique=True)  # Field name made lowercase.
    contactfirstname = models.CharField(db_column='contactFirstName', max_length=255, blank=True, null=True)  # Field name made lowercase.
    contactlastname = models.CharField(db_column='contactLastName', max_length=255, blank=True, null=True)  # Field name made lowercase.
    phone = models.CharField(max_length=255, blank=True, null=True)
    addressline1 = models.CharField(db_column='addressLine1', max_length=255, blank=True, null=True)  # Field name made lowercase.
    addressline2 = models.CharField(db_column='addressLine2', max_length=255, blank=True, null=True)  # Field name made lowercase.
    city = models.CharField(max_length=255, blank=True, null=True)
    state = models.CharField(max_length=255, blank=True, null=True)
    postalcode = models.CharField(db_column='postalCode', max_length=255, blank=True, null=True)  # Field name made lowercase.
    country = models.CharField(max_length=255, blank=True, null=True)

    class Meta:
        managed = True
        db_table = 'Users'


class Userstest(models.Model):
    userid = models.AutoField(db_column='userId', primary_key=True)  # Field name made lowercase.
    customername = models.CharField(db_column='customerName', max_length=255)  # Field name made lowercase.
    contactfirstname = models.CharField(db_column='contactFirstName', max_length=255, blank=True, null=True)  # Field name made lowercase.
    contactlastname = models.CharField(db_column='contactLastName', max_length=255, blank=True, null=True)  # Field name made lowercase.
    phone = models.CharField(max_length=255, blank=True, null=True)
    addressline1 = models.CharField(db_column='addressLine1', max_length=255, blank=True, null=True)  # Field name made lowercase.
    addressline2 = models.CharField(db_column='addressLine2', max_length=255, blank=True, null=True)  # Field name made lowercase.
    city = models.CharField(max_length=255, blank=True, null=True)
    state = models.CharField(max_length=255, blank=True, null=True)
    postalcode = models.CharField(db_column='postalCode', max_length=255, blank=True, null=True)  # Field name made lowercase.
    country = models.CharField(max_length=255, blank=True, null=True)

    class Meta:
        managed = True
        db_table = 'UsersTest'


class AuditLog(models.Model):
    request_ip_address = models.CharField(max_length=256, blank=True, null=True)
    response_code = models.IntegerField(blank=True, null=True)
    request_duration_ms = models.IntegerField(blank=True, null=True)
    url = models.CharField(max_length=256, blank=True, null=True)
    parameters = models.CharField(max_length=256, blank=True, null=True)
    timestamp = models.DateTimeField()
    request_type = models.CharField(max_length=11, blank=True, null=True)

    class Meta:
        managed = True
        db_table = 'audit_log'


class AuthGroup(models.Model):
    name = models.CharField(unique=True, max_length=80)

    class Meta:
        managed = False
        db_table = 'auth_group'


class AuthGroupPermissions(models.Model):
    group = models.ForeignKey(AuthGroup, models.DO_NOTHING)
    permission = models.ForeignKey('AuthPermission', models.DO_NOTHING)

    class Meta:
        managed = False
        db_table = 'auth_group_permissions'
        unique_together = (('group', 'permission'),)


class AuthPermission(models.Model):
    name = models.CharField(max_length=255)
    content_type = models.ForeignKey('DjangoContentType', models.DO_NOTHING)
    codename = models.CharField(max_length=100)

    class Meta:
        managed = False
        db_table = 'auth_permission'
        unique_together = (('content_type', 'codename'),)


class AuthUser(models.Model):
    password = models.CharField(max_length=128)
    last_login = models.DateTimeField(blank=True, null=True)
    is_superuser = models.IntegerField()
    username = models.CharField(unique=True, max_length=30)
    first_name = models.CharField(max_length=30)
    last_name = models.CharField(max_length=30)
    email = models.CharField(max_length=254)
    is_staff = models.IntegerField()
    is_active = models.IntegerField()
    date_joined = models.DateTimeField()

    class Meta:
        managed = False
        db_table = 'auth_user'


class AuthUserGroups(models.Model):
    user = models.ForeignKey(AuthUser, models.DO_NOTHING)
    group = models.ForeignKey(AuthGroup, models.DO_NOTHING)

    class Meta:
        managed = False
        db_table = 'auth_user_groups'
        unique_together = (('user', 'group'),)


class AuthUserUserPermissions(models.Model):
    user = models.ForeignKey(AuthUser, models.DO_NOTHING)
    permission = models.ForeignKey(AuthPermission, models.DO_NOTHING)

    class Meta:
        managed = False
        db_table = 'auth_user_user_permissions'
        unique_together = (('user', 'permission'),)


class DjangoAdminLog(models.Model):
    action_time = models.DateTimeField()
    object_id = models.TextField(blank=True, null=True)
    object_repr = models.CharField(max_length=200)
    action_flag = models.SmallIntegerField()
    change_message = models.TextField()
    content_type = models.ForeignKey('DjangoContentType', models.DO_NOTHING, blank=True, null=True)
    user = models.ForeignKey(AuthUser, models.DO_NOTHING)

    class Meta:
        managed = False
        db_table = 'django_admin_log'


class DjangoContentType(models.Model):
    app_label = models.CharField(max_length=100)
    model = models.CharField(max_length=100)

    class Meta:
        managed = False
        db_table = 'django_content_type'
        unique_together = (('app_label', 'model'),)


class DjangoMigrations(models.Model):
    app = models.CharField(max_length=255)
    name = models.CharField(max_length=255)
    applied = models.DateTimeField()

    class Meta:
        managed = False
        db_table = 'django_migrations'


class DjangoSession(models.Model):
    session_key = models.CharField(primary_key=True, max_length=40)
    session_data = models.TextField()
    expire_date = models.DateTimeField()

    class Meta:
        managed = False
        db_table = 'django_session'
