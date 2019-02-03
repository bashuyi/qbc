
/* Drop Tables */

DROP TABLE IF EXISTS auth_api_param;
DROP TABLE IF EXISTS auth_role_operation;
DROP TABLE IF EXISTS auth_api_operation;
DROP TABLE IF EXISTS auth_api;
DROP TABLE IF EXISTS auth_application;
DROP TABLE IF EXISTS auth_user_role;
DROP TABLE IF EXISTS auth_role;
DROP TABLE IF EXISTS auth_user;




/* Create Tables */

-- API
CREATE TABLE auth_api
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
	-- 名称
	name varchar(50),
	-- 表示名
	display_name varchar(50),
	-- 描述
	description varchar(200),
	-- 应用ID
	application_id bigint,
	PRIMARY KEY (id)
) WITHOUT OIDS;


-- API操作
CREATE TABLE auth_api_operation
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
	-- 名称
	name varchar(50),
	-- 表示名
	display_name varchar(50),
	-- 描述
	description varchar(200),
	-- API ID
	api_id bigint,
	PRIMARY KEY (id)
) WITHOUT OIDS;


-- API参数
CREATE TABLE auth_api_param
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
	-- 名称
	name varchar(50),
	-- 表示名
	display_name varchar(50),
	-- 描述
	description varchar(200),
	-- 类型名
	type_name varchar(100),
	-- 必须
	required boolean,
	-- API操作ID
	operation_id bigint,
	PRIMARY KEY (id)
) WITHOUT OIDS;


-- 应用
CREATE TABLE auth_application
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
	-- 名称
	name varchar(50),
	-- 表示名
	display_name varchar(50),
	-- 描述
	description varchar(200),
	PRIMARY KEY (id)
) WITHOUT OIDS;


-- 角色
CREATE TABLE auth_role
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
	-- 名称
	name varchar(50),
	-- 表示名
	display_name varchar(50),
	-- 描述
	description varchar(200),
	PRIMARY KEY (id)
) WITHOUT OIDS;


-- 角色操作
CREATE TABLE auth_role_operation
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
	-- 角色ID
	role_id bigint,
	-- API操作ID
	operation_id bigint,
	PRIMARY KEY (id)
) WITHOUT OIDS;


-- 用户
CREATE TABLE auth_user
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
	-- 用户名
	username varchar(50),
	-- 密码
	password varchar(50),
	-- 签名密钥
	secret varchar(50),
	-- 表示名
	display_name varchar(50),
	PRIMARY KEY (id)
) WITHOUT OIDS;


-- 用户角色
CREATE TABLE auth_user_role
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
	-- 用户ID
	user_id bigint,
	-- 角色ID
	role_id bigint,
	PRIMARY KEY (id)
) WITHOUT OIDS;



/* Comments */

COMMENT ON TABLE auth_api IS 'API';
COMMENT ON COLUMN auth_api.id IS 'ID';
COMMENT ON COLUMN auth_api.created_by IS '创建者';
COMMENT ON COLUMN auth_api.created_date_time IS '创建时间';
COMMENT ON COLUMN auth_api.last_modified_by IS '修改者';
COMMENT ON COLUMN auth_api.last_modified_date_time IS '修改时间';
COMMENT ON COLUMN auth_api.deleted IS '已删除';
COMMENT ON COLUMN auth_api.name IS '名称';
COMMENT ON COLUMN auth_api.display_name IS '表示名';
COMMENT ON COLUMN auth_api.description IS '描述';
COMMENT ON COLUMN auth_api.application_id IS '应用ID';
COMMENT ON TABLE auth_api_operation IS 'API操作';
COMMENT ON COLUMN auth_api_operation.id IS 'ID';
COMMENT ON COLUMN auth_api_operation.created_by IS '创建者';
COMMENT ON COLUMN auth_api_operation.created_date_time IS '创建时间';
COMMENT ON COLUMN auth_api_operation.last_modified_by IS '修改者';
COMMENT ON COLUMN auth_api_operation.last_modified_date_time IS '修改时间';
COMMENT ON COLUMN auth_api_operation.deleted IS '已删除';
COMMENT ON COLUMN auth_api_operation.name IS '名称';
COMMENT ON COLUMN auth_api_operation.display_name IS '表示名';
COMMENT ON COLUMN auth_api_operation.description IS '描述';
COMMENT ON COLUMN auth_api_operation.api_id IS 'API ID';
COMMENT ON TABLE auth_api_param IS 'API参数';
COMMENT ON COLUMN auth_api_param.id IS 'ID';
COMMENT ON COLUMN auth_api_param.created_by IS '创建者';
COMMENT ON COLUMN auth_api_param.created_date_time IS '创建时间';
COMMENT ON COLUMN auth_api_param.last_modified_by IS '修改者';
COMMENT ON COLUMN auth_api_param.last_modified_date_time IS '修改时间';
COMMENT ON COLUMN auth_api_param.deleted IS '已删除';
COMMENT ON COLUMN auth_api_param.name IS '名称';
COMMENT ON COLUMN auth_api_param.display_name IS '表示名';
COMMENT ON COLUMN auth_api_param.description IS '描述';
COMMENT ON COLUMN auth_api_param.type_name IS '类型名';
COMMENT ON COLUMN auth_api_param.required IS '必须';
COMMENT ON COLUMN auth_api_param.operation_id IS 'API操作ID';
COMMENT ON TABLE auth_application IS '应用';
COMMENT ON COLUMN auth_application.id IS 'ID';
COMMENT ON COLUMN auth_application.created_by IS '创建者';
COMMENT ON COLUMN auth_application.created_date_time IS '创建时间';
COMMENT ON COLUMN auth_application.last_modified_by IS '修改者';
COMMENT ON COLUMN auth_application.last_modified_date_time IS '修改时间';
COMMENT ON COLUMN auth_application.deleted IS '已删除';
COMMENT ON COLUMN auth_application.name IS '名称';
COMMENT ON COLUMN auth_application.display_name IS '表示名';
COMMENT ON COLUMN auth_application.description IS '描述';
COMMENT ON TABLE auth_role IS '角色';
COMMENT ON COLUMN auth_role.id IS 'ID';
COMMENT ON COLUMN auth_role.created_by IS '创建者';
COMMENT ON COLUMN auth_role.created_date_time IS '创建时间';
COMMENT ON COLUMN auth_role.last_modified_by IS '修改者';
COMMENT ON COLUMN auth_role.last_modified_date_time IS '修改时间';
COMMENT ON COLUMN auth_role.deleted IS '已删除';
COMMENT ON COLUMN auth_role.name IS '名称';
COMMENT ON COLUMN auth_role.display_name IS '表示名';
COMMENT ON COLUMN auth_role.description IS '描述';
COMMENT ON TABLE auth_role_operation IS '角色操作';
COMMENT ON COLUMN auth_role_operation.id IS 'ID';
COMMENT ON COLUMN auth_role_operation.created_by IS '创建者';
COMMENT ON COLUMN auth_role_operation.created_date_time IS '创建时间';
COMMENT ON COLUMN auth_role_operation.last_modified_by IS '修改者';
COMMENT ON COLUMN auth_role_operation.last_modified_date_time IS '修改时间';
COMMENT ON COLUMN auth_role_operation.deleted IS '已删除';
COMMENT ON COLUMN auth_role_operation.role_id IS '角色ID';
COMMENT ON COLUMN auth_role_operation.operation_id IS 'API操作ID';
COMMENT ON TABLE auth_user IS '用户';
COMMENT ON COLUMN auth_user.id IS 'ID';
COMMENT ON COLUMN auth_user.created_by IS '创建者';
COMMENT ON COLUMN auth_user.created_date_time IS '创建时间';
COMMENT ON COLUMN auth_user.last_modified_by IS '修改者';
COMMENT ON COLUMN auth_user.last_modified_date_time IS '修改时间';
COMMENT ON COLUMN auth_user.deleted IS '已删除';
COMMENT ON COLUMN auth_user.username IS '用户名';
COMMENT ON COLUMN auth_user.password IS '密码';
COMMENT ON COLUMN auth_user.secret IS '签名密钥';
COMMENT ON COLUMN auth_user.display_name IS '表示名';
COMMENT ON TABLE auth_user_role IS '用户角色';
COMMENT ON COLUMN auth_user_role.id IS 'ID';
COMMENT ON COLUMN auth_user_role.created_by IS '创建者';
COMMENT ON COLUMN auth_user_role.created_date_time IS '创建时间';
COMMENT ON COLUMN auth_user_role.last_modified_by IS '修改者';
COMMENT ON COLUMN auth_user_role.last_modified_date_time IS '修改时间';
COMMENT ON COLUMN auth_user_role.deleted IS '已删除';
COMMENT ON COLUMN auth_user_role.user_id IS '用户ID';
COMMENT ON COLUMN auth_user_role.role_id IS '角色ID';



