
/* Drop Tables */

DROP TABLE IF EXISTS common_message;




/* Create Tables */

-- 消息
CREATE TABLE common_message
(
	-- ID
	id bigint NOT NULL,
	-- 创建者
	created_by bigint,
	-- 创建时间
	created_date_time timestamp,
	-- 修改者
	last_modified_by bigint,
	-- 修改时间
	last_modified_date_time timestamp,
	-- 已删除
	deleted boolean,
	-- 消息编码
	code varchar(50),
	-- 地区
	locale varchar(50),
	-- 消息文本
	text varchar(500),
	PRIMARY KEY (id)
) WITHOUT OIDS;



/* Comments */

COMMENT ON TABLE common_message IS '消息';
COMMENT ON COLUMN common_message.id IS 'ID';
COMMENT ON COLUMN common_message.created_by IS '创建者';
COMMENT ON COLUMN common_message.created_date_time IS '创建时间';
COMMENT ON COLUMN common_message.last_modified_by IS '修改者';
COMMENT ON COLUMN common_message.last_modified_date_time IS '修改时间';
COMMENT ON COLUMN common_message.deleted IS '已删除';
COMMENT ON COLUMN common_message.code IS '消息编码';
COMMENT ON COLUMN common_message.locale IS '地区';
COMMENT ON COLUMN common_message.text IS '消息文本';



