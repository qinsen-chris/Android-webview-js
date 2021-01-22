class GFormula{
    
    constructor(){
        //自定义公式
        this.formula;
        //系统公式名称
        this.systemFormulaName;
    }

    /**
     * 设置公式所需要的依赖数据
     * @param {string} key       
     * @param {GDataSet} value 
     */
    setParam(key,value){}

    /**
     * 运行公式
     */
    run(){}

}

    // /**
    //  * 设置计算公式
    //  * @param {string} formula 
    //  * @param {GDataSet} data 
    //  */
    // runFormula(formula,data){};

    // /**
    //  * 系统公式计算
    //  * @param {string} formulaName KDJ
    //  * @param {GDataSet} data 
    //  */
    // runSystemFormula(formulaName,data){};
