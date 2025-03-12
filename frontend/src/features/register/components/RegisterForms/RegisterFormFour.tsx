import React from 'react'
import { Checkbox } from '../../../../components/Checkbox/Checkbox'
import { DropDown } from '../../../../components/DropDown/DropDown'
import { ValidatedTextInput } from '../../../../components/ValidatedInput/ValidatedTextInput'
import { countryCodeDropDown } from '../../utils/RegisterModalUtils'
import { useEffect, useState } from 'react'
import { validatePhone } from '../../../../services/Validators'
import { useDispatch, useSelector } from 'react-redux'
import { AppDispatch, RootState } from '../../../../redux/store'
import {  updateRegister } from '../../../../redux/Slices/RegisterSlice'

import "./RegisterForm.css"
import "../../../../assets/global.css"


export const RegisterFormFour:React.FC = () => {

    const state = useSelector((state:RootState) => state.register)

    const [phoneCode, setPhoneCode] = useState<string>("+1")
    const [phoneNumber, setPhoneNumber] = useState<string>("")
    const [validNumber, setValidNumber] = useState<boolean>(true)


    const dispatch:AppDispatch = useDispatch();


    const changeCode = (e:React.ChangeEvent<HTMLSelectElement>) => {
        setPhoneCode(e.target.value.split(" ")[0]);
    }

    const changePhoneNumber = (e:React.ChangeEvent<HTMLInputElement>) => {
        setPhoneNumber(e.target.value);

        dispatch(updateRegister({
            name: "phoneNumber",
            value: e.target.value
        }));
    }


    useEffect(() => {
      
        if(phoneNumber){
            setValidNumber(validatePhone(phoneNumber));
            dispatch(updateRegister({
                name: "phoneNumberValid",
                value: validatePhone(phoneNumber)
        }));
        }
   
    }, [phoneCode, phoneNumber])

  return (
    <div className='register-container'>

        <div className='register-content'>

            <h1  className='register-header-2'>Add a phone Number</h1>
            <p className='register-text color-gray'>Enter the phone number you would like to associate with your Fwitter account. You wont get a verification code sent here.</p>
            <div className={validNumber ? "register-four-input-wrapper" : "register-four-input-wrapper-condensed"}>
                <DropDown 
                
                    content={countryCodeDropDown}
                    change={changeCode}    
                    label={"Country Code"}
                    defaultValue={"US +1"} 
                    />
                    <ValidatedTextInput 
                    valid = {true}
                    name={'phoneNumber'}
                    label={"Your Phone Number"}
                    changeValue={changePhoneNumber}
                    />
                    {validNumber ? <></> : <p className='register-error color-red'>Please Enter a valid 10 digit number</p>}
            </div>
            <div className='register-four-checkbox-wrapper'>
                <p className='register-text color-gray'>Let People who have your phone number find and connect with you on Fwitter. <span className='register-link color-blue'>Learn More</span>.</p>
                <Checkbox/>
            </div>
            <div className='register-four-checkbox-wrapper'>
                <p className='register-text color-gray'>Let Fwitter use your phone number to personalize our servives, including ads (if permitted by your Ads preferences). If you dont enable this, Fwitter will still use your phone number for purposes including account security, spam, fraud and abuse preventional <span className='register-link color-blue'>See our Privacy Policy for more information.</span>.</p>
                <Checkbox/>
            </div>

        </div>
      
    </div>
  )
}
