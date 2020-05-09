import { notification } from 'antd';

export const handleApiError = (error, message) => {
  if (error.hasOwnProperty('response') && error.response.status === 401) {
    const redirectUrl = encodeURI(window.location.href);
    window.location.href =
      window.location.origin + `/admin/login?redirect=${redirectUrl}`;
  } else {
    notification.error({
      message: 'There was a problem',
      duration: 10,
      description: message,
    });
  }
};
