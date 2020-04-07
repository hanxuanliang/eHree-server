# eHree-server

eHree 后端项目，演进化推进

## JPA查询

特地说一下这个 `jpa` 查询问题。一对多，多对多查询；`JPQL` 语句查询。都是 `jpa` 比较复杂的问题。

### 连接查询

连接查询这一般就涉及到一对多，多对多的问题。来看看 `jpa` 的处理：

- 一对多

    分单向一对多，双向一对多。说白了就是你需要在哪边开始查询。单向一对多，只能是一方启动查询，但是多方没办法查询到一方，也就有了双向一对多。但是一般不用双向的。
    
    ```java
    @Entity
    @Table(name = "spu")
    public class Spu extends BaseEntity{
        @Id
        private Long id;
        ...
            
        /**
         * 至于什么时候加上这个一对多，多对多的导航关联关系，看你是否需要跨表查询，
         * 如果需要，则可以加上一对多这些注解。设置呢，一般是设置是 “一” 这一方。
         * 记得把关联的键加上：@JoinColumn(name = "yourKeyName")。
         *
         * 因为你设置关联导航关系，JPA就会帮你识别出来，而不需要你自己去书写SQL就可以
         * 达到跨表查询，这个是及其方便的。
         *
         * 下面还有一个问题：就是设置了 LAZY加载，虽然在代码里面没有显示去触发skuList
         * 的获取，但是在序列化到前端的过程是会触发 getSkuList()，所以也就是设置也会
         * 读取。
         */
        @OneToMany(fetch = FetchType.LAZY)
        @JoinColumn(name = "spuId")
        private List<Sku> skuList;
    	...
    }
    ```
    
- 多对多

    先看看单向多对多：

    ```java
    @Entity
    public class Theme extends BaseEntity{
        @Id
        private Long id;
        ...
    
        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(name="theme_spu",joinColumns = @JoinColumn(name="theme_id"),
                inverseJoinColumns = @JoinColumn(name="spu_id"))
        private List<Spu> spuList;
    }
    ```

    可以看到这个 `@ManyToMany` 是标注在一方就行【就是随便哪一方都行的】。然后 `@JoinTable` 是对多对多的这个第三张表做了规范。而注意：【**标注了 `@ManyToMany` 就作为主控方，所以就看谁是主控那就标注在谁上面**】

    所以就出现了双向多对多：

    ```java
    @Entity
    public class Coupon extends BaseEntity {
        @Id
        private Long id;
        
        @ManyToMany(fetch = FetchType.LAZY, mappedBy = "couponList")
        private List<Category> categoryList;
    }
    ```

    ```java
    @Entity
    public class Category extends BaseEntity{
        @Id
        private Long id;
    	...
    
        // 双向多对多如果需要规范生成的第三张表，就需要标注 @JoinTable
        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(name = "coupon_category",
                joinColumns = @JoinColumn(name = "category_id"),
                inverseJoinColumns = @JoinColumn(name = "coupon_id"))
        private List<Coupon> couponList;
    }
    ```

所以我们可以看到谁是关系被维护端，谁就需要标注这个 `mappedBy = "column"`。

**多对多的关系维护方还是被维护方，这个哪个对象标注都是可以的。**

**但是一对多上，维护方就是多方，被维护方式一方。**

## 订单模块

细节真的太多了，可以说是目前设计最复杂的地方，携带的参数很多 ~~~ :fire:

### 校验参数

需要校验哪些参数呢？

- 商品     无货/有货
- 商品最大购买量     总数量限制/单品数量限制
- 前后端计算的价格是否一致     优惠前/优惠后【 totalPrice / finalTotalPrice 】
- 优惠券校验     是否拥有/是否过期

### 价格参数

- 前端

- 后端 

