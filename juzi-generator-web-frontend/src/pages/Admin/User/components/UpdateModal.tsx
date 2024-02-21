import { updateUserUsingPost } from '@/services/frontend-init/userController';
import { ProFormSelect, ProFormText, ProFormTextArea } from '@ant-design/pro-components';
import '@umijs/max';
import { Form, message, Modal } from 'antd';
import { useForm } from 'antd/es/form/Form';
import React, { useEffect } from 'react';

interface Props {
  oldData?: API.User;
  visible: boolean;
  onSubmit: (values: API.UserAddRequest) => void;
  onCancel: () => void;
}

/**
 * 更新节点
 *
 * @param fields
 */
const handleUpdate = async (fields: API.UserUpdateRequest) => {
  const hide = message.loading('正在编辑');
  try {
    const res = await updateUserUsingPost(fields);
    hide();
    if (res.code === 0) {
      message.success('编辑成功');
      return true;
    } else {
      message.error(res.message);
      return false;
    }
  } catch (error: any) {
    hide();
    message.error('编辑失败，' + error.message);
    return false;
  }
};

/**
 * 更新弹窗
 * @param props
 * @constructor
 */
const UpdateModal: React.FC<Props> = (props) => {
  const { oldData, visible, onSubmit, onCancel } = props;

  const [form] = useForm();

  useEffect(() => {
    form.setFieldsValue({
      userAccount: oldData?.userAccount,
      userName: oldData?.userName,
      userProfile: oldData?.userProfile,
      userRole: oldData?.userRole,
    });
  });

  if (!oldData) {
    return <></>;
  }

  const onOk = async () => {
    const value: any = form.getFieldsValue() as API.UserUpdateRequest;
    const success = await handleUpdate({
      ...value,
      id: oldData.id,
    }); //表单中的内容传递给函数
    if (success) {
      onSubmit?.(value);
    }
  };

  return (
    <Modal
      destroyOnClose //配合destroyOnClose+reserve={false}才能保证表单内容清除
      title={'编辑'}
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
export default UpdateModal;
