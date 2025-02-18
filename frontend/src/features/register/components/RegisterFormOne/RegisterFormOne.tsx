import React, {useState, useEffect} from 'react'
import { validateName } from '../../../../services/Validators';
import { ValidatedInput } from '../../../../components/ValidatedInput/ValidatedInput';

import './RegisterFormOne.css'
import { RegisterDateInput } from '../RegisterDateInput/RegisterDateInput';
import { RegisterNameInputs } from '../RegisterNameInput/RegisterNameInputs';
import { RegisterEmailInput } from '../RegisterEmailInput/RegisterEmailInput';

interface FormOneState {
    firstName : string;
    lastName: string;
    email: string;
    dateOfBirth: string;
}

export const RegisterFormOne:React.FC = () => {


    const [stepOneState, setStepOneState] = useState<FormOneState>({

        firstName: "",
        lastName: "",
        email: "",
        dateOfBirth:""
    });

    const updateUser = (e:React.ChangeEvent<HTMLInputElement>): void => {
        setStepOneState({...stepOneState, [e.target.name]: e.target.value});
    }

    useEffect(()=>{
        console.log("state changed : ",stepOneState)
    },[stepOneState])

  return (
    <div className='reg-step-one-container'>
        <div className='reg-step-one-content'>
     
          <RegisterNameInputs/>
          <RegisterEmailInput/>
          <RegisterDateInput />
        </div>
    </div>
  )
}
