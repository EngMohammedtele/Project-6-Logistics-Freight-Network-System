# Logistics & Freight Network

مشروع Java 17 وSpring Boot 3.5.5، يحتوي 16 Entity وDTO وRepository وService وController لكل كيان.
الأكواد منسقة. Controller يستقبل الطلب، Service ينفذ المنطق، Repository يحفظ في قاعدة البيانات، DTO ينقل البيانات.

## تشغيل IntelliJ وMySQL
1. فك الضغط وافتح مجلد logistics-network الذي يحتوي pom.xml.
2. اختر JDK 17 وانتظر تحميل Maven.
3. في MySQL Workbench نفذ: CREATE DATABASE IF NOT EXISTS logistics_db;
4. افتح Run ثم Edit Configurations ثم Environment variables وأدخل DB_USERNAME وDB_PASSWORD بقيم اتصالك.
5. شغّل LogisticsApplication في com.codelegends.logistics.
6. انتظر Started LogisticsApplication. المنفذ الافتراضي 8080.
7. استورد Logistics.postman_collection.json في Postman.
8. شغّل مجلدات 01 ثم 02 ثم 03 ثم 04. المتغيرات تحفظ IDs تلقائيًا.
9. مجلد 05 مرجع CRUD للاختبار اليدوي على سجلات منفصلة؛ ليس سيناريو تشغيل متسلسل.

## تشغيل سريع بدون MySQL
اختر Active profiles: demo في IntelliJ، أو نفذ:
./mvnw spring-boot:run -Dspring-boot.run.profiles=demo
Windows: mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=demo
يستخدم H2 مؤقتة وتُفقد بياناتها عند إيقاف التطبيق.

## الاختبار والبناء
./mvnw test
./mvnw package
Windows: mvnw.cmd test

## CRUD
لكل مورد POST /api/resource وGET /api/resource وGET /api/resource/{id} وPUT /api/resource/{id} وDELETE /api/resource/{id}.
PUT يستقبل كامل DTO. GET يخفي بيانات الاتصال والرخصة. عند PUT أعد إدخال الحقول الخاصة المطلوبة.
الموارد:
- warehouses
- products
- inventory-items
- customers
- service-zones
- addresses
- carriers
- vehicles
- drivers
- shipments
- shipment-items
- routes
- delivery-stops
- tracking-events
- invoices
- staff

## العمليات
POST /api/operations/shipments يحجز المخزون ويحسب الوزن؛ عند فشل أي عنصر تتراجع المعاملة كاملة.
PUT /api/operations/shipments/{id}/carrier يغير الناقل قبل تعيين مسار.
POST /api/operations/routes يحجز المركبة والسائق المتاحين من الناقل نفسه.
POST /api/operations/routes/{id}/stops يفحص التسلسل ووزن جميع الشحنات.
POST /api/operations/shipments/{id}/tracking يضيف الحدث ويحدث حالة الشحنة.
PUT /api/operations/stops/{id}/complete مع {"completed":true} يكمل الوقفة؛ آخر وقفة تكمل المسار وتحرر المركبة والسائق.
POST /api/operations/shipments/{id}/invoice مع {"amount":25} ينشئ فاتورة لشحنة تم تسليمها.

## قرارات التصميم
- سعة Warehouse تقاس بعدد وحدات المخزون؛ Vehicle بالكيلوغرام.
- العنوان يتبع ServiceZone واحدة لتوضيح العلاقة التي تركها التكليف عامة.
- عنوان الوقفة نص ثابت عند التخطيط.
- الناقل مطلوب عند إنشاء الشحنة ويمكن إعادة تعيينه قبل المسار.
- المبلغ يحدده طلب إنشاء الفاتورة؛ التكليف لم يحدد معادلة تسعير.
- الأرقام المالية تظهر فقط في DTO الفاتورة والإحصائيات التي تحتاجها، والتواريخ المعروضة تواريخ عمليات مطلوبة. حقول BaseClass الداخلية لا تعرض.
- قيود SKU ولوحة المركبة والرخصة والمخزون والفاتورة والتسلسل محفوظة حتى بعد Soft Delete؛ استخدم قيمة جديدة للسجل الجديد.
- تحديث الحالة يتم عبر عمليات الأعمال، وCRUD يرفض تعديلات تخالف المخزون أو التعيين.
- هذا نموذج تدريب محلي بلا Authentication؛ لا يحتوي تكامل دفع أو خرائط حقيقية.

## التأكد من الجداول والتسليم
نفذ USE logistics_db; ثم SHOW TABLES; المفترض 16 جدولًا.
لمشاهدة العلاقات استخدم Database > Reverse Engineer داخل MySQL Workbench واختر logistics_db.
احفظ صورة المخطط الحقيقي الناتج على جهازك. schema.mmd مخطط توضيحي وليس لقطة MySQL.
صدّر Postman بعد اختباراتك وأضف نتائجك الفعلية في DEBUGGING_LOG.txt.
ارفع المستودع بعد ضبط حساب GitHub الخاص بك؛ لا ترفع كلمة مرور قاعدة البيانات.
التكليف يذكر أن استخدام AI مقصور على الصياغة: هذه نسخة مرجعية للتعلم ويجب مراعاة شروط تسليمك.
