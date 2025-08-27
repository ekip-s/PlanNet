import styles from './PowerPlan.module.css';
import { useEffect, useState } from 'react';
import { send } from '../../../api/sendHTTP.tsx';
import { useAuth } from '../../../../keycloak/AuthContext.tsx';
import { useNavigate } from 'react-router';

const PowerPlan = () => {
  const { getToken } = useAuth();
  const [isInit, sendIsInit] = useState<boolean>();
  const navigate = useNavigate();

  useEffect(() => {
    send<boolean>({
      url: ``,
      service: 'power',
      token: getToken(),
      setDataInfo: sendIsInit,
      dataType: 'boolean',
    });
  }, []);

  useEffect(() => {
    if (isInit == false) {
      navigate('/profile/power');
    }
  }, [isInit]);

  return <div className={styles.powerPlan}>PowerPlan</div>;
};

export default PowerPlan;
