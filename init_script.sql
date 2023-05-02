CREATE EXTENSION IF NOT EXISTS age;

LOAD 'age';

--admin user
INSERT INTO public.tb_member
(member_id, email, "password", username)
VALUES('bitnine1', 'bitnine1@bitnine.net', '1111', 'ADMIN유저');

-- initial role
INSERT INTO public.tb_role
(data_access_yn, role_description, role_name)
VALUES('Y', 'ADMIN권한', 'ADMIN');

--grant role

INSERT INTO public.tb_authority
(member_id, role_id)
VALUES('bitnine1', (SELECT role_id FROM tb_role WHERE role_name = 'ADMIN'));

INSERT INTO public.tb_role
(data_access_yn, role_description, role_name)
VALUES('Y', 'INITIAL ROLE', 'DEFAULT');

INSERT INTO public.tb_role
(data_access_yn, role_description, role_name)
VALUES('Y', 'ADD MEMBER', 'ADD_MEMBER');

INSERT INTO public.tb_role
(data_access_yn, role_description, role_name)
VALUES('N', 'UPDATE MEMBER', 'UPDATE_MEMBER');

INSERT INTO public.tb_role
(data_access_yn, role_description, role_name)
VALUES('N', 'DETAIL MEMBER', 'DETAIL_MEMBER');

INSERT INTO public.tb_role
(data_access_yn, role_description, role_name)
VALUES('N', 'DELETE MEMBER', 'DELETE_MEMBER');

INSERT INTO public.tb_role
(data_access_yn, role_description, role_name)
VALUES('Y', 'LIST MEMBER', 'LIST_MEMBER');

INSERT INTO public.tb_role
(data_access_yn, role_description, role_name)
VALUES('N', 'ADD ROLE', 'ADD_ROLE');

INSERT INTO public.tb_role
(data_access_yn, role_description, role_name)
VALUES('N', 'UPDATE ROLE', 'UPDATE_ROLE');

INSERT INTO public.tb_role
(data_access_yn, role_description, role_name)
VALUES('N', 'DELETE ROLE', 'DELETE_ROLE');

INSERT INTO public.tb_role
(data_access_yn, role_description, role_name)
VALUES('N', 'LIST ROLE', 'LIST_ROLE');

INSERT INTO public.tb_role
(data_access_yn, role_description, role_name)
VALUES('Y', 'ADD GROUP', 'ADD_GROUP');

INSERT INTO public.tb_role
(data_access_yn, role_description, role_name)
VALUES('Y', 'UPDATE GROUP', 'UPDATE_GROUP');

INSERT INTO public.tb_role
(data_access_yn, role_description, role_name)
VALUES('Y', 'DETAIL GROUP', 'DETAIL_GROUP');

INSERT INTO public.tb_role
(data_access_yn, role_description, role_name)
VALUES('Y', 'DELETE GROUP', 'DELETE_GROUP');

--initial menu
INSERT INTO public.tb_menu
(menu_url, role_id)
VALUES('/api/v1/member/add', (SELECT role_id FROM tb_role WHERE role_name = 'ADD_MEMBER'));

INSERT INTO public.tb_menu
(menu_url, role_id)
VALUES('/api/v1/member/update', (SELECT role_id FROM tb_role WHERE role_name = 'UPDATE_MEMBER'));

INSERT INTO public.tb_menu
(menu_url, role_id)
VALUES('/api/v1/member/detail', (SELECT role_id FROM tb_role WHERE role_name = 'DETAIL_MEMBER'));

INSERT INTO public.tb_menu
(menu_url, role_id)
VALUES('/api/v1/member/delete', (SELECT role_id FROM tb_role WHERE role_name = 'DELETE_MEMBER'));

INSERT INTO public.tb_menu
(menu_url, role_id)
VALUES('/api/v1/member/list', (SELECT role_id FROM tb_role WHERE role_name = 'LIST_MEMBER'));

INSERT INTO public.tb_menu
(menu_url, role_id)
VALUES('/api/v1/role/add', (SELECT role_id FROM tb_role WHERE role_name = 'ADD_ROLE'));

INSERT INTO public.tb_menu
(menu_url, role_id)
VALUES('/api/v1/role/update', (SELECT role_id FROM tb_role WHERE role_name = 'UPDATE_ROLE'));

INSERT INTO public.tb_menu
(menu_url, role_id)
VALUES('/api/v1/role/delete', (SELECT role_id FROM tb_role WHERE role_name = 'DELETE_ROLE'));

INSERT INTO public.tb_menu
(menu_url, role_id)
VALUES('/api/v1/role/list', (SELECT role_id FROM tb_role WHERE role_name = 'LIST_ROLE'));

INSERT INTO public.tb_menu
(menu_url, role_id)
VALUES('/api/v1/group/add', (SELECT role_id FROM tb_role WHERE role_name = 'ADD_GROUP'));

INSERT INTO public.tb_menu
(menu_url, role_id)
VALUES('/api/v1/group/update', (SELECT role_id FROM tb_role WHERE role_name = 'UPDATE_GROUP'));

INSERT INTO public.tb_menu
(menu_url, role_id)
VALUES('/api/v1/group/detail', (SELECT role_id FROM tb_role WHERE role_name = 'DETAIL_GROUP'));

INSERT INTO public.tb_menu
(menu_url, role_id)
VALUES('/api/v1/group/delete', (SELECT role_id FROM tb_role WHERE role_name = 'DELETE_GROUP'));



