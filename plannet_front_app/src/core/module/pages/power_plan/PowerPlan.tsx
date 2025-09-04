import styles from './PowerPlan.module.css';
import { useEffect, useState } from 'react';
import { send } from '../../../api/sendHTTP.tsx';
import { useAuth } from '../../../../keycloak/AuthContext.tsx';
import { useNavigate } from 'react-router';
import TabMenuSelect from '../../atoms/tab_menu/TabMenuSelect.tsx';
import { Outlet } from 'react-router-dom';

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
    } else {
      navigate('/powerPlan/mealPlan');
    }
  }, [isInit]);

  const items = [
    {
      label: 'Питание',
      icon: 'pi pi-home',
      command: () => {
        navigate('/powerPlan/mealPlan');
      },
    },
    {
      label: 'План тренировок',
      icon: 'pi pi-user-plus',
      command: () => {
        navigate('/powerPlan/workout');
      },
    },
  ];

  const pathMap = ['/powerPlan/mealPlan', '/powerPlan/workout'];
  const activeIndex = pathMap.findIndex((path) => location.pathname === path);

  return (
    <div className={styles.powerPlan}>
      <TabMenuSelect items={items} activeIndex={activeIndex === -1 ? 0 : activeIndex} />
      <Outlet />
    </div>
  );
};

export default PowerPlan;
