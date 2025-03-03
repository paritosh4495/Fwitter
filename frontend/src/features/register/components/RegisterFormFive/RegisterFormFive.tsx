import React from 'react'
import { useEffect, useState } from 'react'
import { useDispatch, UseDispatch, useSelector } from 'react-redux'
import { ValidatedTextInput } from '../../../../components/ValidatedInput/ValidatedTextInput'
import { RootState } from '../../../../redux/store'
import { StyledNextButton } from '../RegisterNextButton/RegisterNextButton'
import './RegisterFormFive.css'
import { resendEmail } from '../../../../redux/Slices/RegisterSlice'
import { AppDispatch } from '../../../../redux/store'
import { sendVerification } from '../../../../redux/Slices/RegisterSlice'

export const RegisterFormFive:React.FC = () => {


    const state = useSelector((state: RootState) => state.register);

    const dispatch:AppDispatch = useDispatch();

    const [code, setCode] = useState<string>('')

    const handleChange = (e:React.ChangeEvent<HTMLInputElement>) => {
        setCode(e.target.value)
    }

    const resend = () => {
        dispatch(
            resendEmail(state.username)
        )
    };

    const verify = () => {

        dispatch(
            sendVerification({
                username: state.username,
                code: code
            })
        )
    };


  return (

    <div className="reg-step-five-container">
        
        <div className='reg-step-five-content'>

            <h1>We Sent you a code</h1>
            <p>Enter it below to verify {state.email}</p>

            <ValidatedTextInput valid={true}  name={"code"} label={"Verification Code"} changeValue={handleChange}/>
            <p className='reg-ste-five-message' onClick={resend} >Didnt revieve a email ?</p>

            <StyledNextButton active={code ? true : false} disabled={code ? false: true} color={'black'} onClick={verify}>
                Next
                </StyledNextButton>

        </div>

    </div>
  )
}
