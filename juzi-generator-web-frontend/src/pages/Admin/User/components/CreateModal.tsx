import { addUserUsingPost } from '@/services/frontend-init/userController';
import { ProFormSelect, ProFormText, ProFormTextArea } from '@ant-design/pro-components';
import '@umijs/max';
import { Form, message, Modal } from 'antd';
import { useForm } from 'antd/es/form/Form';
import React from 'react';

interface Props {
  visible: boolean;
  onSubmit: (values: API.UserAddRequest) => void;
  onCancel: () => void;
}

/**
 * 添加节点
 * @param fields
 */
const handleAdd = async (fields: API.UserAddRequest) => {
  const hide = message.loading('正在添加');
  try {
    const res = await addUserUsingPost(fields);
    hide();
    if (res.code === 0) {
      message.success('添加成功');
      return true;
    } else {
      message.error(res.message);
      return false;
    }
  } catch (error: any) {
    hide();
    message.error('创建失败，' + error.message);
    return false;
  }
};

/**
 * 创建弹窗
 * @param props
 * @constructor
 */
const CreateModal: React.FC<Props> = (props) => {
  const { visible, onSubmit, onCancel } = props;
  const [form] = useForm();
  const onOk = async () => {
    //获取表单数据
    const values = form.getFieldsValue() as API.UserAddRequest;
    const success = await handleAdd(values);
    if (success) {
      onSubmit?.(values);
    }
  };
  return (
    <Modal
      destroyOnClose //配合destroyOnClose+reserve={false}才能保证表单内容清除
      title={'新建'}
      open={visible} //控制弹窗是否显示
      onCancel={onCancel} //点击取消才执行onCancel函数
      onOk={onOk} //点击确定才执行onOk函数
    >
      {/*autoComplete取消浏览器自动填充*/}
      <Form autoComplete="off" form={form} preserve={false}>
        <ProFormText
          rules={[
            {
              required: true,
              message: '账号不能为空',
            },
          ]}
          width="md"
          name="userAccount"
          label="账号"
        />
        <ProFormText
          rules={[
            {
              required: true,
              message: '用户名不能为空',
            },
          ]}
          width="md"
          name="userName"
          label="用户名"
        />
        <ProFormTextArea
          rules={[
            {
              required: true,
              message: '简介不能为空',
            },
          ]}
          name="userProfile"
          label="简介"
        ></ProFormTextArea>

        <ProFormSelect
          rules={[
            {
              required: true,
              message: '角色不能为空',
            },
          ]}
          width="md"
          name="userRole"
          label="角色"
          valueEnum={{
            user: { text: '普通用户' },
            admin: { text: '管理员' },
          }}
        />
      </Form>
    </Modal>
  );
};
export default CreateModal;
