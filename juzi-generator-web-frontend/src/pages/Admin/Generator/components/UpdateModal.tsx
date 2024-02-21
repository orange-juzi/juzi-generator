import { updateGeneratorUsingPost } from '@/services/frontend-init/generatorController';
import { ProFormSelect, ProFormText, ProFormTextArea } from '@ant-design/pro-components';
import '@umijs/max';
import { Form, message, Modal } from 'antd';
import { useForm } from 'antd/es/form/Form';
import React, { useEffect } from 'react';

interface Props {
  oldData?: API.Generator;
  visible: boolean;
  onSubmit: (values: API.GeneratorUpdateRequest) => void;
  onCancel: () => void;
}

/**
 * 更新节点
 *
 * @param fields
 */
const handleUpdate = async (fields: API.GeneratorUpdateRequest) => {
  fields.fileConfig = JSON.parse((fields.fileConfig || '{}') as string);
  fields.modelConfig = JSON.parse((fields.modelConfig || '{}') as string);
  const hide = message.loading('正在编辑');
  try {
    const res = await updateGeneratorUsingPost(fields);
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
      name: oldData?.name,
      description: oldData?.description,
      basePackage: oldData?.basePackage,
      version: oldData?.version,
      author: oldData?.author,
      tags: JSON.parse(oldData?.tags || '[]'),
      picture: oldData?.picture,
      fileConfig: oldData?.fileConfig,
      modelConfig: oldData?.modelConfig,
      distPath: oldData?.distPath,
      status: oldData?.status,
      userId: oldData?.userId,
    });
  });

  if (!oldData) {
    return <></>;
  }

  const onOk = async () => {
    const value: any = form.getFieldsValue() as API.GeneratorUpdateRequest;
    const success = await handleUpdate({
      ...value,
      id: oldData.id,
    }); //表单中的内容传递给函数
    if (success) {
      onSubmit?.(value);
    }
  };

  const handleChange = (value: string) => {
    console.log(`selected ${value}`);
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
      <Form autoComplete="off" form={form} preserve={false} layout="vertical">
        <ProFormText width="md" name="name" label="名称" />
        <ProFormText width="md" name="description" label="描述" />
        <ProFormText width="md" name="basePackage" label="基础包" />
        <ProFormText width="md" name="version" label="版本" />
        <ProFormText width="md" name="author" label="作者" />
        <ProFormSelect
          width="md"
          mode="tags"
          name="tags"
          label="标签"
          onChange={handleChange}
        ></ProFormSelect>
        <ProFormText width="md" name="image" label="图片" />
        <ProFormTextArea width="md" name="fileConfig" label="文件配置" />
        <ProFormTextArea width="md" name="modelConfig" label="模型配置" />
        <ProFormText width="md" name="distPath" label="产物包路径" />
        <ProFormSelect
          width="md"
          name="status"
          label="状态"
          valueEnum={{
            0: {
              text: '默认',
            },
          }}
        />
      </Form>
    </Modal>
  );
};
export default UpdateModal;
