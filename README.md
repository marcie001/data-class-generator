# Data Class Generator

Generate Java data class files.

## Examples

Prepare database.

```
$ docker container run -p 3306:3306  --rm -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=sample mysql:5.7.19
```

```
mysql> create table products(id char(36) not null primary key, name varchar(100) not null, description text, unit_price integer, created datetime not null, updated datetime not null);
mysql> create table orders(id char(36) not null primary key, order_date date not null, ship_date date);
mysql> create table order_details(order_id char(36) not null, product_id char(36) not null, quantity integer not null, unit_price bigint not null, primary key (order_id, product_id), foreign key (order_id) references orders (id) on delete cascade, foreign key (product_id) references products (id));
```

Run this application.

```
$ ./mvnw clean spring-boot:run -Dgenerator.data-class-type=lombok -Dgenerator.target-schema=sample -Dgenerator.java-package=com.example.foo.model -Dspring.profiles.active=mysql
```

It will generate java files as follows.

`generated-data-classes/com/example/foo/model/Products.java`


```java
package com.example.foo.model;

import java.io.Serializable;
import java.sql.Timestamp;
import lombok.Builder;
import lombok.Data;

/**
 * class for the products database table.
 *
 */
@Data
@Builder
public class Products implements Serializable {

        private static final long serialVersionUID = 1L;

        private String id;

        private String name;

        private String description;

        private int unitPrice;

        private Timestamp created;

        private Timestamp updated;

}
```

`generated-data-classes/com/example/foo/model/Orders.java`


```java
package com.example.foo.model;

import java.io.Serializable;
import java.sql.Date;
import lombok.Builder;
import lombok.Data;

/**
 * class for the orders database table.
 *
 */
@Data
@Builder
public class Orders implements Serializable {

        private static final long serialVersionUID = 1L;

        private String id;

        private Date orderDate;

        private Date shipDate;

}
```

`generated-data-classes/com/example/foo/model/OrderDetails.java`

```java
package com.example.foo.model;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

/**
 * class for the order_details database table.
 *
 */
@Data
@Builder
public class OrderDetails implements Serializable {

        private static final long serialVersionUID = 1L;

        private String orderId;

        private String productId;

        private int quantity;

        private long unitPrice;

}
```
