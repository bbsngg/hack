<template>
  <el-form ref="form"  :model="userForm" label-width="160px">
    <el-form-item label="学校">
      <el-input v-model="userForm.school"></el-input>
  </el-form-item>

 
    <el-form-item label="年级">
      <el-input v-model="userForm.grade"></el-input>
  </el-form-item>


    <el-form-item label="邮箱">
      <el-input v-model="userForm.email"></el-input>
  </el-form-item>


    <el-form-item label="QQ">
      <el-input v-model="userForm.qq"></el-input>
  </el-form-item>

  <el-form-item label="微信">
      <el-input v-model="userForm.wechat"></el-input>
  </el-form-item>
  
  <el-form-item size="small" class="me-login-button">
          <el-button type="primary" @click.native.prevent="set('userForm')">设置</el-button>
  </el-form-item>

  </el-form>
  
</template>

<script>
  import Axios from "axios"
  export default {
    name: 'MessageBoard',
    data() {
      return {
        userForm:{
          school:'',
          grade:'',
          email:'',
          qq:'',
          wechat:''
        }
      }
    },
    methods:{
      set(formname){
        
        var url = "http://localhost:8888/users/setEmail"
        this.$axios(url,{
          
          email:this.userForm.email+"",
          school:this.userForm.school,
          grade:this.userForm.grade,
          QQ:this.userForm.qq,
          wechat:this.userForm.wechat
        },{
          emulateJSON:true
        }).then(function(data){
          console.log("success");
        }).catch(function(){
          console.log("fail");
        });
      }
    },
    mounted(){
      
    },
    computed: {
      title (){
        return '留言板 - For Fun'
      }
    }
    
    
  }
</script>
