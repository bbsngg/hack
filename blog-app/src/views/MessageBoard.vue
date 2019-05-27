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
          <el-button type="primary" @click.native.prevent="setEmail('userForm')">设置</el-button>
  </el-form-item>

  </el-form>
  
</template>

<script>
  import Axios from "axios"
  import {setEmail} from '@/api/login'
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
      setEmail(formname){
        let that = this
         that.$store.dispatch('setEmail', that.userForm).then(() => {
              // that.$message({message: '注册成功 快写文章吧', type: 'success', showClose: true});
              alert("Success");
              that.$router.push({path: '/'})
            }).catch((error) => {
              if (error !== 'error') {
                alert(error);
              }
            })

        // method:'post'
        // var url = "http://localhost:8888/users/setEmail/"
        // this.$axios(url,{
          
        //   email:this.userForm.email+"",
          
        //   school:this.userForm.school,
        //   grade:this.userForm.grade,
        //   QQ:this.userForm.qq,
        //   wechat:this.userForm.wechat
        // },{
        //   emulateJSON:true
        // }).then(function(data){
        //   console.log("success");
        // }).catch(function(){
        //   console.log("fail");
        //   console.log(this.userForm);
        // });
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
