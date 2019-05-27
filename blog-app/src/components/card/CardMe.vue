<template>
  <el-card>
    <h1 id="name" class="me-author-name">{{shimh}}</h1>
    <div class="me-author-description">
      <span id="school"><i class="el-icon-location-outline"></i> &nbsp;{{school}}</span>
      <span id="grade"><i class="me-icon-job"></i> &nbsp;{{grade}}</span>
    </div>
    <div class="me-author-tool">
      <i @click="showTool(qq)" :title="qq.title" class="me-icon-QQ"></i>
      <i @click="showTool(github)" :title="github.title" class="me-icon-github"></i>
    </div>
  </el-card>

</template>

<script>
  export default {
    name: 'CardMe',
   
    data() {
      return {
 
        shimh:this.$store.state.name,
        school:"南京大学",
        grade:"大二",
        qq: {title: 'QQ', message: '919431514'},
        github: {
          title: 'github',
          message: '<a target="_blank" href="https://github.com/shimh-develop">https://github.com/shimh-develop</a>'
        }
      }
    },
    mounted(){
     
    },
    methods: {
      showTool(tool) {
        this.$message({
          duration: 0,
          showClose: true,
          dangerouslyUseHTMLString: true,
          message: '<strong>' + tool.message + '</strong>'
        });
      }

    }
  }
</script>

<style scoped>
  .me-author-name {
    text-align: center;
    font-size: 30px;
    border-bottom: 1px solid #5FB878;
  }

  .me-author-description {
    padding: 8px 0;
  }

  .me-icon-job {
    padding-left: 16px;
  }

  .me-author-tool {
    text-align: center;
    padding-top: 10px;
  }

  .me-author-tool i {
    cursor: pointer;
    padding: 4px 10px;
    font-size: 30px;
  }
</style>
