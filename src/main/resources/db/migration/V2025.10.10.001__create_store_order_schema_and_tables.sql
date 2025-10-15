-- Create schema and tables for JPA models that use schema `store_order`
CREATE SCHEMA IF NOT EXISTS store_order;

CREATE TABLE IF NOT EXISTS store_order.orders (
  id_order    varchar(36) PRIMARY KEY,
  date        timestamptz NOT NULL,
  id_user     varchar(36) NOT NULL,
  total       decimal(12,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS store_order.item (
  id_item     varchar(36) PRIMARY KEY,
  id_order    varchar(36) NOT NULL REFERENCES store_order.orders(id_order) ON DELETE CASCADE,
  id_product  varchar(36) NOT NULL,
  quantity    integer NOT NULL,
  item_price  decimal(12,2) NOT NULL
);
