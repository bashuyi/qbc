
/* Drop Tables */

DROP TABLE IF EXISTS common_dictionary;
DROP TABLE IF EXISTS common_dictionary_type;
DROP TABLE IF EXISTS common_message;
DROP TABLE IF EXISTS common_system_parameter;




/* Create Tables */

-- 数据字典
CREATE TABLE common_dictionary
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
	-- 编码
	code varchar(50),
	-- 语言
	lang varchar(50),
	-- 内容
	content varchar(500),
	-- 表示顺序
	display_order int,
	-- 数据字典类型ID
	dictionary_type_id bigint,
	PRIMARY KEY (id)
) WITHOUT OIDS;


-- 数据字典类型
CREATE TABLE common_dictionary_type
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
	-- 表示名
	display_name varchar(50),
	-- 描述
	description varchar(200),
	PRIMARY KEY (id)
) WITHOUT OIDS;


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
	-- 编码
	code varchar(50),
	-- 语言
	lang varchar(50),
	-- 内容
	content varchar(500),
	PRIMARY KEY (id)
) WITHOUT OIDS;


-- 系统变量
CREATE TABLE common_system_parameter
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
	-- 编码
	code varchar(50),
	-- 表示名
	display_name varchar(50),
	-- 内容
	content varchar(500),
	-- 描述
	description varchar(200),
	PRIMARY KEY (id)
) WITHOUT OIDS;



/* Comments */

COMMENT ON TABLE common_dictionary IS '数据字典';
COMMENT ON COLUMN common_dictionary.id IS 'ID';
COMMENT ON COLUMN common_dictionary.created_by IS '创建者';
COMMENT ON COLUMN common_dictionary.created_date_time IS '创建时间';
COMMENT ON COLUMN common_dictionary.last_modified_by IS '修改者';
COMMENT ON COLUMN common_dictionary.last_modified_date_time IS '修改时间';
COMMENT ON COLUMN common_dictionary.deleted IS '已删除';
COMMENT ON COLUMN common_dictionary.code IS '编码';
COMMENT ON COLUMN common_dictionary.lang IS '语言';
COMMENT ON COLUMN common_dictionary.content IS '内容';
COMMENT ON COLUMN common_dictionary.display_order IS '表示顺序';
COMMENT ON COLUMN common_dictionary.dictionary_type_id IS '数据字典类型ID';
COMMENT ON TABLE common_dictionary_type IS '数据字典类型';
COMMENT ON COLUMN common_dictionary_type.id IS 'ID';
COMMENT ON COLUMN common_dictionary_type.created_by IS '创建者';
COMMENT ON COLUMN common_dictionary_type.created_date_time IS '创建时间';
COMMENT ON COLUMN common_dictionary_type.last_modified_by IS '修改者';
COMMENT ON COLUMN common_dictionary_type.last_modified_date_time IS '修改时间';
COMMENT ON COLUMN common_dictionary_type.deleted IS '已删除';
COMMENT ON COLUMN common_dictionary_type.display_name IS '表示名';
COMMENT ON COLUMN common_dictionary_type.description IS '描述';
COMMENT ON TABLE common_message IS '消息';
COMMENT ON COLUMN common_message.id IS 'ID';
COMMENT ON COLUMN common_message.created_by IS '创建者';
COMMENT ON COLUMN common_message.created_date_time IS '创建时间';
COMMENT ON COLUMN common_message.last_modified_by IS '修改者';
COMMENT ON COLUMN common_message.last_modified_date_time IS '修改时间';
COMMENT ON COLUMN common_message.deleted IS '已删除';
COMMENT ON COLUMN common_message.code IS '编码';
COMMENT ON COLUMN common_message.lang IS '语言';
COMMENT ON COLUMN common_message.content IS '内容';
COMMENT ON TABLE common_system_parameter IS '系统变量';
COMMENT ON COLUMN common_system_parameter.id IS 'ID';
COMMENT ON COLUMN common_system_parameter.created_by IS '创建者';
COMMENT ON COLUMN common_system_parameter.created_date_time IS '创建时间';
COMMENT ON COLUMN common_system_parameter.last_modified_by IS '修改者';
COMMENT ON COLUMN common_system_parameter.last_modified_date_time IS '修改时间';
COMMENT ON COLUMN common_system_parameter.deleted IS '已删除';
COMMENT ON COLUMN common_system_parameter.code IS '编码';
COMMENT ON COLUMN common_system_parameter.display_name IS '表示名';
COMMENT ON COLUMN common_system_parameter.content IS '内容';
COMMENT ON COLUMN common_system_parameter.description IS '描述';



