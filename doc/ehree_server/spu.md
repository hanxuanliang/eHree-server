# (spu)
| 列名 | 类型 | KEY | 可否为空 | 默认值 | 注释 |
| ---- | ---- | ---- | ---- | ---- | ----  |
| id | int(10) unsigned | PRI | NO |  |  |
| title | varchar(100) |  | NO |  |  |
| subtitle | varchar(800) |  | YES |  |  |
| category_id | int(10) unsigned |  | NO |  |  |
| root_category_id | int(11) |  | YES |  |  |
| online | tinyint(3) unsigned |  | NO |  |  |
| create_time | datetime(3) |  | YES |  |  |
| update_time | datetime(3) |  | YES |  |  |
| delete_time | datetime(3) |  | YES |  |  |
| price | varchar(20) |  | NO |  | 文本型价格，有时候SPU需要展示的是一个范围，或者自定义平均价格 |
| sketch_spec_id | int(10) unsigned |  | YES |  | 某种规格可以直接附加单品图片 |
| default_sku_id | int(11) |  | YES |  | 默认选中的sku |
| img | varchar(255) |  | YES |  |  |
| discount_price | varchar(20) |  | YES |  |  |
| description | varchar(255) |  | YES |  |  |
| tags | varchar(30) |  | YES |  |  |
| is_test | tinyint(3) unsigned |  | YES |  |  |
| spu_theme_img | json |  | YES |  |  |
| for_theme_img | varchar(255) |  | YES |  |  |