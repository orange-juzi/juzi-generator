import { addGeneratorUsingPost } from '@/services/frontend-init/generatorController';
import { ProFormSelect, ProFormText, ProFormTextArea } from '@ant-design/pro-components';
import '@umijs/max';
import { Form, message, Modal } from 'antd';
import { useForm } from 'antd/es/form/Form';
import React from 'react';

interface Props {
  visible: boolean;
  onSubmit: (values: API.GeneratorAddRequest) => void;
  onCancel: () => void;
}

/**
 * 添加节点
 * @param fields
 */
const handleAdd = async (fields: API.GeneratorAddRequest) => {
  fields.fileConfig = JSON.parse((fields.fileConfig || '{}') as string);
  fields.modelConfig = JSON.parse((fields.modelConfig || '{}') as string);
  const hide = message.loading('正在添加');
  try {
    const res = await addGeneratorUsingPost(fields);
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
    const values = form.getFieldsValue() as API.GeneratorAddRequest;
    const success = await handleAdd(values);
    if (success) {
      onSubmit?.(values);
    }
  };

  const handleChange = (value: string) => {
    console.log(`selected ${value}`);
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
export default CreateModal;
