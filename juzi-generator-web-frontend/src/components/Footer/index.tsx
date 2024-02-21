import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-components';
import React from 'react';

const Footer: React.FC = () => {
  const defaultMessage = '归属橘子所有权';
  const currentYear = new Date().getFullYear();
  return (
    <DefaultFooter
      style={{
        background: 'none',
      }}
      copyright={`${currentYear} ${defaultMessage}`}
      links={[
        {
          key: '橘子通用模版',
          title: '橘子通用模版',
          href: '',
          blankTarget: true,
        },
        {
          key: 'github',
          title: <GithubOutlined />,
          href: 'https://github.com/orange-juzi',
          blankTarget: true,
        },
        {
          key: 'juzi',
          title: '橘子',
          href: '',
          blankTarget: true,
        },
      ]}
    />
  );
};

export default Footer;
