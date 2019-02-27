CREATE TABLE item (
    item_id integer NOT NULL,
    quantity integer,
    total_price double precision,
    unit_price double precision,
    order_id integer NOT NULL,
    product_id integer NOT NULL
);

CREATE SEQUENCE item_item_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE order_store (
    order_id integer NOT NULL,
    distance double precision,
    freight_value double precision,
    state character varying(255),
    total_products_value double precision
);

CREATE SEQUENCE order_store_order_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE product (
    product_id integer NOT NULL,
    price double precision,
    product_name character varying(255),
    product_type character varying(255),
    weight double precision
);

ALTER TABLE ONLY item
    ADD CONSTRAINT item_pkey PRIMARY KEY (item_id);

ALTER TABLE ONLY order_store
    ADD CONSTRAINT order_store_pkey PRIMARY KEY (order_id);

ALTER TABLE ONLY product
    ADD CONSTRAINT product_pkey PRIMARY KEY (product_id);

ALTER TABLE ONLY item
    ADD CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES order_store(order_id);

ALTER TABLE ONLY item
    ADD CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product(product_id);