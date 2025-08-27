import styles from './PowerSettings.module.css';
import global from '../../../../global.module.css';
import { useEffect, useState } from 'react';
import { InputTextarea } from 'primereact/inputtextarea';
import { Card } from 'primereact/card';
import { InputText } from 'primereact/inputtext';
import { Dropdown } from 'primereact/dropdown';
import { Checkbox } from 'primereact/checkbox';
import { InputNumber, InputNumberValueChangeEvent } from 'primereact/inputnumber';
import Button from '../../atoms/btns/Button.tsx';
import { send } from '../../../api/sendHTTP.tsx';
import Error from '../../molecules/error/Error.tsx';
import { useAuth } from '../../../../keycloak/AuthContext.tsx';
import useApi from '../../../api/useApi.tsx';
import Loading from '../../molecules/loading/Loading.tsx';
import { UserInfoModel } from '../../../models/UserInfoModel.ts';
import { useNavigate } from 'react-router';

const PowerSettings = () => {
  const navigate = useNavigate();
  const [aiTunnelToken, setAiTunnelToken] = useState<string>('');
  const [selectedModel, setSelectedModel] = useState(modelList[0]);
  const [currentWeight, setCurrentWeight] = useState<number | null>();
  const [desiredWeight, setDesiredWeight] = useState<number | null>();
  const [updateCheckbox, setUpdateCheckbox] = useState<boolean>(false);
  const [trainPlanInput, setTrainPlanInput] = useState<string>();
  const [mealPlanInput, setMealPlanInput] = useState<string>();
  const [sendError, setError] = useState<string>('');
  const { getToken } = useAuth();
  const { data, loading, refresh } = useApi<UserInfoModel>({
    url: '/info',
    service: 'power',
  });
  const changed =
    data && !Array.isArray(data)
      ? aiTunnelToken == data.aiToken &&
        selectedModel.code == data.model &&
        currentWeight == data.currentWeight &&
        desiredWeight == data.desiredWeight &&
        updateCheckbox == data.automaticUpdate &&
        trainPlanInput == data.trainPlanOptions &&
        mealPlanInput == data.mealPlanOptions
      : false;

  useEffect(() => {
    if (!data) return;
    if (Array.isArray(data)) return;

    setAiTunnelToken(data.aiToken);
    const index = modelList.findIndex((model) => model.code === data.model);
    setSelectedModel(index !== -1 ? modelList[index] : modelList[0]);
    setCurrentWeight(data.currentWeight);
    setDesiredWeight(data.desiredWeight);
    setUpdateCheckbox(data.automaticUpdate);
    setTrainPlanInput(data.trainPlanOptions);
    setMealPlanInput(data.mealPlanOptions);
  }, [data]);

  const formSubmit = () => {
    setError('');
    send({
      url: '/balance',
      service: 'ai_tunnel',
      token: aiTunnelToken,
      setError,
    });
    saveOrUpdateInfo();
    refresh();
  };

  const saveOrUpdateInfo = () => {
    send({
      url: '',
      service: 'power',
      method: 'POST',
      body: {
        aiToken: aiTunnelToken,
        model: selectedModel.code,
        currentWeight: currentWeight,
        desiredWeight: desiredWeight,
        automaticUpdate: updateCheckbox,
        trainPlanOptions: trainPlanInput,
        mealPlanOptions: mealPlanInput,
      },
      token: getToken(),
      dataType: 'not',
    });
  };

  const toPlanHandler = () => {
    navigate('/powerPlan');
  };

  //ОРГАНИЗОВАТЬ КНОПКУ ПЕРЕХОДА К ЭКРАНУ ИНИТ;

  if (loading) {
    return <Loading />;
  }

  return (
    <div className={styles.powerSettings}>
      {sendError && <Error message={'Ошибка проверки токена, проверь, что верно его ввел'} />}
      <form action={formSubmit}>
        <h4>Основное:</h4>
        <Card className={global.card}>
          <div className={global.flexAndSpaceBetween}>
            <div>
              <div>
                <label htmlFor="aiTunnelToken">Токен:</label>
                <InputText
                  className={global.customInputText}
                  type={'password'}
                  value={aiTunnelToken}
                  id={'aiTunnelToken'}
                  placeholder={'sk-aitunnel-...'}
                  required={true}
                  onChange={(e) => setAiTunnelToken(e.target.value)}
                />
              </div>
              <div className={styles.box}>
                <Dropdown
                  placeholder={'Выбери модель'}
                  value={selectedModel}
                  onChange={(e) => setSelectedModel(e.target.value)}
                  options={modelList}
                  panelClassName={global.customDropdownPanel}
                  optionLabel="code"
                  required={true}
                  className={global.dropdownPanel}
                />
              </div>
            </div>
            <div className={styles.textWrapper}>
              <div>
                Сервис работает с{' '}
                <a href="https://aitunnel.ru" target="_blank" rel="noopener noreferrer">
                  aitunnel.ru
                </a>
                . Чтобы пользоваться сервисом, зарегистрируйтесь, введите токен и выберите модель.
              </div>
            </div>
          </div>
          <div>
            <div className={styles.mrBt}>
              <label htmlFor="current_weight_input">Текущий вес:</label>
              <InputNumber
                inputClassName={`${global.customInputText} ${styles.width}`}
                inputId={'current_weight_input'}
                value={currentWeight}
                onValueChange={(e: InputNumberValueChangeEvent) => setCurrentWeight(e.value)}
                required={true}
                maxFractionDigits={2}
                minFractionDigits={2}
                mode={'decimal'}
                min={10}
                max={300}
              />
            </div>
            <div>
              <label htmlFor="desired_weight_input">Желаемый вес:</label>
              <InputNumber
                inputClassName={`${global.customInputText} ${styles.width}`}
                inputId={'desired_weight_input'}
                value={desiredWeight}
                onValueChange={(e: InputNumberValueChangeEvent) => setDesiredWeight(e.value)}
                maxFractionDigits={2}
                minFractionDigits={2}
                required={true}
                mode={'decimal'}
                min={10}
                max={300}
              />
            </div>
          </div>
          <div className={global.flexAndSpaceBetween}>
            <div className={global.customCheckbox}>
              <Checkbox
                checked={updateCheckbox}
                onChange={(e) => setUpdateCheckbox(e.checked ?? false)}
              />
              <label>Обновлять данные автоматически</label>
            </div>
            <div className={styles.textWrapper}>
              <div>
                Система автоматически будет обновлять планы тренировок. Для безопасности рекомендуем
                ограничить доступную сумму по токену.
              </div>
            </div>
          </div>
        </Card>
        <h4>Настройки планов тренировки:</h4>
        <Card className={global.card}>
          <label htmlFor="train_plan_input_textarea">Опиши пожелания к плану тренировок:</label>
          <InputTextarea
            required={true}
            value={trainPlanInput}
            onChange={(e) => setTrainPlanInput(e.target.value)}
            maxLength={2000}
            id={'train_plan_input_textarea'}
            className={styles.inputTextarea}
            placeholder={
              'Могу ходит в зал один раз в неделю в субботу или воскресенье, в другие дни могу устроить пробежку или делать упражнения дома'
            }
          />
        </Card>
        <h4>Настройки плана питания:</h4>
        <Card className={global.card}>
          <label htmlFor="meal_plan_input_textarea">Опиши пожелания к плану питания:</label>
          <InputTextarea
            required={true}
            value={mealPlanInput}
            onChange={(e) => setMealPlanInput(e.target.value)}
            maxLength={2000}
            id={'meal_plan_input_textarea'}
            className={styles.inputTextarea}
            placeholder={'Не люблю тушенную капусту'}
          />
        </Card>
        <Card className={global.card}>
          <div className={styles.btn}>
            {!data && <Button type={'submit'} text={'Сохранить'} />}
            {data && <Button type={'button'} onClick={toPlanHandler} text={'Перейти к планам'} />}
            {!changed && <Button type={'submit'} text={'Изменить'} className={styles.blueBtn} />}
          </div>
        </Card>
      </form>
    </div>
  );
};

export default PowerSettings;

const modelList = [
  {
    code: 'gpt-4o-mini',
  },
  {
    code: 'gpt-5-mini',
  },
  {
    code: 'gpt-5',
  },
];
