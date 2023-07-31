create table product_tab
(product_id bigInt primary key AUTO_INCREMENT,
product_name varchar(255) not null unique,
product_status varchar(15) not null);

create table productcategory_tab
(
productcategory_id bigInt primary key AUTO_INCREMENT,
productcategory_name varchar(255) not null unique
);

create table prd2prdcat_tab( product_id bigInt,
productcategory_id bigInt,
FOREIGN KEY (product_id) REFERENCES product_tab(product_id)  ON DELETE CASCADE
FOREIGN key (productcategory_id) references productcategory_tab(productcategory_id)  ON DELETE CASCADE
);

create table priceHistory_tab
(
priceHistory_id bigInt primary key AUTO_INCREMENT,
product_id bigInt,
effective_from datetime(6),
is_active enum('ACTIVE','INACTIVE'),
price double not null,
FOREIGN KEY (product_id) REFERENCES product_tab(product_id)  ON DELETE CASCADE
);