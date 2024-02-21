import CreateModal from '@/pages/Admin/User/components/CreateModal';
import UpdateModal from '@/pages/Admin/User/components/UpdateModal';
import {
  deleteUserUsingPost,
  listUserByPageUsingPost,
} from '@/services/frontend-init/userController';
import { ExclamationCircleFilled, PlusOutlined } from '@ant-design/icons';
import type { ActionType, ProColumns } from '@ant-design/pro-components';
import { PageContainer, ProTable } from '@ant-design/pro-components';
import '@umijs/max';
import { Button, message, Modal, Space, TablePaginationConfig } from 'antd';
import { Typography } from 'antd/lib';
import React, { useRef, useState } from 'react';

const UserAdminPage: React.FC = () => {
  // 是否显示新建窗口
  const [createModalVisible, setCreateModalVisible] = useState<boolean>(false);
  // 是否显示更新窗口
  const [updateModalVisible, setUpdateModalVisible] = useState<boolean>(false);

  const actionRef = useRef<ActionType>();
  const [currentRow, setCurrentRow] = useState<API.User>();

  const [current, setCurrent] = useState(1);
  const [pageSize, setPageSize] = useState(10);
  const [total, setTotal] = useState(0);

  /**
   * 删除节点
   */
  const handleDelete = async (row: API.User) => {
    const hide = message.loading('正在删除');
    if (!row) return true;
    try {
      const res = await deleteUserUsingPost({
        id: [row.id] as any,
      });
      hide();
      if (res.code === 0) {
        message.success('删除成功');
        actionRef?.current?.reload();
        return true;
      } else {
        message.error(res.message);
        return false;
      }
    } catch (error: any) {
      hide();
      message.error('删除失败，' + error.message);
      return false;
    }
  };

  const { confirm } = Modal;
  const showDeleteConfirm = (record: API.User) => {
    confirm({
      title: '删除',
      icon: <ExclamationCircleFilled />,
      content: '你确定要删除该用户吗？',
      okText: '确认',
      okType: 'danger',
      cancelText: '取消',
      onOk() {
        handleDelete(record);
      },
      onCancel() {
        console.log('Cancel');
      },
    });
  };

  /**
   * 表格列配置
   */
  const columns: ProColumns<API.User>[] = [
    {
      title: '序号',
      dataIndex: 'id',
      align: 'center',
      hideInSearch: true,
      width: 48,
      render: (_, __, index) => {
        //按顺序排列
        return index + 1 + (current - 1) * pageSize;
      },
    },
    {
      title: '账号',
      dataIndex: 'userAccount',
      valueType: 'text',
      align: 'center',
      hideInSearch: true,
    },
    {
      title: '用户名',
      dataIndex: 'userName',
      align: 'center',
      valueType: 'text',
    },
    {
      title: '头像',
      dataIndex: 'userAvatar',
      align: 'center',
      valueType: 'image',
      hideInSearch: true,
    },
    {
      title: '角色',
      dataIndex: 'userRole',
      align: 'center',
      valueEnum: {
        admin: {
          text: '管理员',
        },
        user: {
          text: '普通用户',
        },
      },
    },
    {
      title: '创建时间',
      sorter: true,
      dataIndex: 'createTime',
      valueType: 'dateTime',
      align: 'center',
      hideInSearch: true,
      hideInForm: true,
    },
    {
      title: '更新时间',
      sorter: true,
      dataIndex: 'updateTime',
      valueType: 'dateTime',
      align: 'center',
      hideInSearch: true,
      hideInForm: true,
    },
    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      align: 'center',
      render: (_, record) => [
        <Space size="middle" key="option">
          <Typography.Link
            onClick={() => {
              setUpdateModalVisible(true);
              setCurrentRow(record);
            }}
          >
            修改
          </Typography.Link>
          <Typography.Link type="danger" onClick={() => showDeleteConfirm(record)}>
            删除
          </Typography.Link>
        </Space>,
      ],
    },
  ];

  // 自定义分页配置
  const customPagination: TablePaginationConfig = {
    pageSizeOptions: ['10', '20', '30', '50'],
    total: total,
    showSizeChanger: true,
    showQuickJumper: true,
    current: current,
    pageSize: pageSize,
    size: 'default',
    showTotal: (totalCount: number) => `共${totalCount} 条`,
  };

  //分页变化事件
  const onChange = (page: any) => {
    setCurrent(page.current); //默认当前页为1，当变化的时候，就需要重新设置值
    setPageSize(page.pageSize); //默认记录数为10，当变化的时候，就需要重新设置值
  };

  return (
    <PageContainer>
      <ProTable<API.User>
        headerTitle={'查询表格'}
        actionRef={actionRef}
        rowKey="key"
        search={{
          labelWidth: 120,
        }}
        onReset={() => {
          //重置按钮，让页面回到第一页
          setCurrent(1);
        }}
        onChange={onChange}
        pagination={customPagination} //自定义分页控件
        toolBarRender={() => [
          <Button
            type="primary"
            key="primary"
            onClick={() => {
              setCreateModalVisible(true);
            }}
          >
            <PlusOutlined /> 新建
          </Button>,
        ]}
        request={async (params, sort, filter) => {
          console.log(sort)
          const sortField = Object.keys(sort)?.[0];
          const sortOrder = sort?.[sortField] ?? undefined;

          const { data, code } = await listUserByPageUsingPost(
            // current: params.current,
            // pageSize: params.pageSize,
            // keyword: params.keyword, 传递查询表单的参数
            {
              ...params,
              sortField,
              sortOrder,
              ...filter,
            } as API.UserQueryRequest,
          );
          setTotal((prevTotal) => Number(data?.total) || prevTotal); //更新总条数
          return {
            success: code === 0,
            data: data?.records,
          };
        }}
        columns={columns}
      />

      <CreateModal
        visible={createModalVisible}
        onSubmit={() => {
          setCreateModalVisible(false);
          actionRef.current?.reload();
        }}
        onCancel={() => {
          setCreateModalVisible(false);
        }}
      />

      <UpdateModal
        visible={updateModalVisible}
        oldData={currentRow}
        onSubmit={() => {
          setUpdateModalVisible(false);
          setCurrentRow(undefined);
          actionRef.current?.reload();
        }}
        onCancel={() => {
          setUpdateModalVisible(false);
        }}
      />
    </PageContainer>
  );
};
export default UserAdminPage;
