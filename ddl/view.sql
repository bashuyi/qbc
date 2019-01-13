create
	view sys_role_resource as select
		sys_api_operation."name" as operation_name,
		sys_api_operation.display_name as operation_display_name,
		sys_api_operation.description as operation_description,
		sys_api."name" as api_name,
		sys_api.display_name as api_display_name,
		sys_api.description as api_description,
		sys_application."name" as application_name,
		sys_application.display_name as application_display_name,
		sys_application.description as application_description,
		sys_role."name" as role_name,
		sys_role.display_name as role_display_name,
		sys_role.description as role_description
	from
		sys_role
	join sys_role_operation on
		sys_role_operation.role_id = sys_role.id
		and sys_role_operation.deleted = false
		and sys_role.deleted = false
	join sys_api_operation on
		sys_api_operation.id = sys_role_operation.operation_id
		and sys_api_operation.deleted = false
	join sys_api on
		sys_api.id = sys_api_operation.api_id
		and sys_api.deleted = false
	join sys_application on
		sys_application.id = sys_api.application_id
		and sys_application.deleted = false;

COMMENT ON VIEW sys_role_resource IS '系统角色资源视图';

COMMENT ON COLUMN sys_role_resource.operation_name IS 'API操作名';
COMMENT ON COLUMN sys_role_resource.operation_display_name IS 'API操作表示名';
COMMENT ON COLUMN sys_role_resource.operation_description IS 'API操作描述';
COMMENT ON COLUMN sys_role_resource.api_name IS 'API名';
COMMENT ON COLUMN sys_role_resource.api_display_name IS 'API表示名';
COMMENT ON COLUMN sys_role_resource.api_description IS 'API描述';
COMMENT ON COLUMN sys_role_resource.application_name IS '应用名';
COMMENT ON COLUMN sys_role_resource.application_display_name IS '应用表示名';
COMMENT ON COLUMN sys_role_resource.application_description IS '应用描述';
COMMENT ON COLUMN sys_role_resource.role_name IS '角色名';
COMMENT ON COLUMN sys_role_resource.role_display_name IS '角色表示名';
COMMENT ON COLUMN sys_role_resource.role_description IS '角色描述';
