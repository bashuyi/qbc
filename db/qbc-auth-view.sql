create
	view auth_role_resource as select
		auth_api_operation."name" as operation_name,
		auth_api_operation.display_name as operation_display_name,
		auth_api_operation.description as operation_description,
		auth_api."name" as api_name,
		auth_api.display_name as api_display_name,
		auth_api.description as api_description,
		auth_application."name" as application_name,
		auth_application.display_name as application_display_name,
		auth_application.description as application_description,
		auth_role."name" as role_name,
		auth_role.display_name as role_display_name,
		auth_role.description as role_description
	from
		auth_role
	join auth_role_operation on
		auth_role_operation.role_id = auth_role.id
		and auth_role_operation.deleted = false
		and auth_role.deleted = false
	join auth_api_operation on
		auth_api_operation.id = auth_role_operation.operation_id
		and auth_api_operation.deleted = false
	join auth_api on
		auth_api.id = auth_api_operation.api_id
		and auth_api.deleted = false
	join auth_application on
		auth_application.id = auth_api.application_id
		and auth_application.deleted = false;

COMMENT ON VIEW auth_role_resource IS '角色资源视图';

COMMENT ON COLUMN auth_role_resource.operation_name IS 'API操作名';
COMMENT ON COLUMN auth_role_resource.operation_display_name IS 'API操作表示名';
COMMENT ON COLUMN auth_role_resource.operation_description IS 'API操作描述';
COMMENT ON COLUMN auth_role_resource.api_name IS 'API名';
COMMENT ON COLUMN auth_role_resource.api_display_name IS 'API表示名';
COMMENT ON COLUMN auth_role_resource.api_description IS 'API描述';
COMMENT ON COLUMN auth_role_resource.application_name IS '应用名';
COMMENT ON COLUMN auth_role_resource.application_display_name IS '应用表示名';
COMMENT ON COLUMN auth_role_resource.application_description IS '应用描述';
COMMENT ON COLUMN auth_role_resource.role_name IS '角色名';
COMMENT ON COLUMN auth_role_resource.role_display_name IS '角色表示名';
COMMENT ON COLUMN auth_role_resource.role_description IS '角色描述';
