package com.jst.vesms.model;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * 
 * <p>Title:报废公司接口获取实体对象</p>
 * <p>Description: 通过webservice调用报废公司的接口，返回的实体对象</p>
 * <p>Copyright: DanRui</p>
 * <p>Company: jst</p>
 * @author DanRui
 * @version 0.0.1
 */
public class RecycleInfoEntity implements Serializable {

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.6
	 */
	private static final long serialVersionUID = 1L;
	
	//号牌号码
	public String chePaiHaoMa;
	
	//号牌种类
	public String chePaiLeiBie;
	
	//厂牌型号
	public String changPaiXingHao;
	
	//车辆类型
	public String cheLiangLeiXing;
	
	//车架号
	public String cheJiaHao;
	
	//发动机号
	public String faDongJiHao;
	
	//使用性质
	public String shiYongXingZhi;
	
	//燃油种类
	public String ranYouLeiXing;
	
	//货车总质量
	public int huoCheZongZhiLiang;
	
	//发动机排量
	public String faDongJiPaiLiang;
	
	//长度
	public float changDu;
	
	//乘坐人数
	public int chengZuoRenShu;
	
	//初次登记日期
	public String chuShiDengJiRiQi;
	
	//交售日期
	public String jiaoShouRiQi;
	
	//回收证明编号
	public String huiShouZhengMingHao;
	
	//车主
	public String cheZhu;
	
	//车主身份证
	public String cheZhuShenFenZheng;
	
	//组织机构代码
	public String zuZhiJiGouDaiMa;
	
	//机构类型
	public String jiGouLeiXing;
	
	//确认书编号
	public String queRenShuBianHao;
	
	//是否个人或企业
	public boolean siRen;
	
	//经办人
	public String jingBanRen;
	
	//经办人电话
	public String jingBanRenDH;
	
	//经办人身份证
	public String jingBanRenSFZ;
	
	//联系地址
	public String lianXiDiZi;
	
	//邮政编码
	public String youZhengBianMa;
	
	/**
	 * chePaiHaoMa.
	 *
	 * @return  the chePaiHaoMa
	 * @since   JDK 1.6
	 */
	public String getChePaiHaoMa() {
		return chePaiHaoMa;
	}

	/**
	 * chePaiHaoMa.
	 *
	 * @param   chePaiHaoMa    the chePaiHaoMa to set
	 * @since   JDK 1.6
	 */
	public void setChePaiHaoMa(String chePaiHaoMa) {
		this.chePaiHaoMa = chePaiHaoMa;
	}

	/**
	 * chePaiLeiBie.
	 *
	 * @return  the chePaiLeiBie
	 * @since   JDK 1.6
	 */
	public String getChePaiLeiBie() {
		return chePaiLeiBie;
	}

	/**
	 * chePaiLeiBie.
	 *
	 * @param   chePaiLeiBie    the chePaiLeiBie to set
	 * @since   JDK 1.6
	 */
	public void setChePaiLeiBie(String chePaiLeiBie) {
		this.chePaiLeiBie = chePaiLeiBie;
	}

	/**
	 * changPaiXingHao.
	 *
	 * @return  the changPaiXingHao
	 * @since   JDK 1.6
	 */
	public String getChangPaiXingHao() {
		return changPaiXingHao;
	}

	/**
	 * changPaiXingHao.
	 *
	 * @param   changPaiXingHao    the changPaiXingHao to set
	 * @since   JDK 1.6
	 */
	public void setChangPaiXingHao(String changPaiXingHao) {
		this.changPaiXingHao = changPaiXingHao;
	}

	/**
	 * cheLiangLeiXing.
	 *
	 * @return  the cheLiangLeiXing
	 * @since   JDK 1.6
	 */
	public String getCheLiangLeiXing() {
		return cheLiangLeiXing;
	}

	/**
	 * cheLiangLeiXing.
	 *
	 * @param   cheLiangLeiXing    the cheLiangLeiXing to set
	 * @since   JDK 1.6
	 */
	public void setCheLiangLeiXing(String cheLiangLeiXing) {
		this.cheLiangLeiXing = cheLiangLeiXing;
	}

	/**
	 * cheJiaHao.
	 *
	 * @return  the cheJiaHao
	 * @since   JDK 1.6
	 */
	public String getCheJiaHao() {
		return cheJiaHao;
	}

	/**
	 * cheJiaHao.
	 *
	 * @param   cheJiaHao    the cheJiaHao to set
	 * @since   JDK 1.6
	 */
	public void setCheJiaHao(String cheJiaHao) {
		this.cheJiaHao = cheJiaHao;
	}

	/**
	 * faDongJiHao.
	 *
	 * @return  the faDongJiHao
	 * @since   JDK 1.6
	 */
	public String getFaDongJiHao() {
		return faDongJiHao;
	}

	/**
	 * faDongJiHao.
	 *
	 * @param   faDongJiHao    the faDongJiHao to set
	 * @since   JDK 1.6
	 */
	public void setFaDongJiHao(String faDongJiHao) {
		this.faDongJiHao = faDongJiHao;
	}

	/**
	 * shiYongXingZhi.
	 *
	 * @return  the shiYongXingZhi
	 * @since   JDK 1.6
	 */
	public String getShiYongXingZhi() {
		return shiYongXingZhi;
	}

	/**
	 * shiYongXingZhi.
	 *
	 * @param   shiYongXingZhi    the shiYongXingZhi to set
	 * @since   JDK 1.6
	 */
	public void setShiYongXingZhi(String shiYongXingZhi) {
		this.shiYongXingZhi = shiYongXingZhi;
	}

	/**
	 * ranYouLeiXing.
	 *
	 * @return  the ranYouLeiXing
	 * @since   JDK 1.6
	 */
	public String getRanYouLeiXing() {
		return ranYouLeiXing;
	}

	/**
	 * ranYouLeiXing.
	 *
	 * @param   ranYouLeiXing    the ranYouLeiXing to set
	 * @since   JDK 1.6
	 */
	public void setRanYouLeiXing(String ranYouLeiXing) {
		this.ranYouLeiXing = ranYouLeiXing;
	}

	/**
	 * huoCheZongZhiLiang.
	 *
	 * @return  the huoCheZongZhiLiang
	 * @since   JDK 1.6
	 */
	public int getHuoCheZongZhiLiang() {
		return huoCheZongZhiLiang;
	}

	/**
	 * huoCheZongZhiLiang.
	 *
	 * @param   huoCheZongZhiLiang    the huoCheZongZhiLiang to set
	 * @since   JDK 1.6
	 */
	public void setHuoCheZongZhiLiang(int huoCheZongZhiLiang) {
		this.huoCheZongZhiLiang = huoCheZongZhiLiang;
	}

	/**
	 * faDongJiPaiLiang.
	 *
	 * @return  the faDongJiPaiLiang
	 * @since   JDK 1.6
	 */
	public String getFaDongJiPaiLiang() {
		return faDongJiPaiLiang;
	}

	/**
	 * faDongJiPaiLiang.
	 *
	 * @param   faDongJiPaiLiang    the faDongJiPaiLiang to set
	 * @since   JDK 1.6
	 */
	public void setFaDongJiPaiLiang(String faDongJiPaiLiang) {
		this.faDongJiPaiLiang = faDongJiPaiLiang;
	}

	/**
	 * changDu.
	 *
	 * @return  the changDu
	 * @since   JDK 1.6
	 */
	public float getChangDu() {
		return changDu;
	}

	/**
	 * changDu.
	 *
	 * @param   changDu    the changDu to set
	 * @since   JDK 1.6
	 */
	public void setChangDu(float changDu) {
		this.changDu = changDu;
	}

	/**
	 * chengZuoRenShu.
	 *
	 * @return  the chengZuoRenShu
	 * @since   JDK 1.6
	 */
	public int getChengZuoRenShu() {
		return chengZuoRenShu;
	}

	/**
	 * chengZuoRenShu.
	 *
	 * @param   chengZuoRenShu    the chengZuoRenShu to set
	 * @since   JDK 1.6
	 */
	public void setChengZuoRenShu(int chengZuoRenShu) {
		this.chengZuoRenShu = chengZuoRenShu;
	}

	/**
	 * chuShiDengJiRiQi.
	 *
	 * @return  the chuShiDengJiRiQi
	 * @since   JDK 1.6
	 */
	public String getChuShiDengJiRiQi() {
		return chuShiDengJiRiQi;
	}

	/**
	 * chuShiDengJiRiQi.
	 *
	 * @param   chuShiDengJiRiQi    the chuShiDengJiRiQi to set
	 * @since   JDK 1.6
	 */
	public void setChuShiDengJiRiQi(String chuShiDengJiRiQi) {
		this.chuShiDengJiRiQi = chuShiDengJiRiQi;
	}

	/**
	 * jiaoShouRiQi.
	 *
	 * @return  the jiaoShouRiQi
	 * @since   JDK 1.6
	 */
	public String getJiaoShouRiQi() {
		return jiaoShouRiQi;
	}

	/**
	 * jiaoShouRiQi.
	 *
	 * @param   jiaoShouRiQi    the jiaoShouRiQi to set
	 * @since   JDK 1.6
	 */
	public void setJiaoShouRiQi(String jiaoShouRiQi) {
		this.jiaoShouRiQi = jiaoShouRiQi;
	}

	/**
	 * huiShouZhengMingHao.
	 *
	 * @return  the huiShouZhengMingHao
	 * @since   JDK 1.6
	 */
	public String getHuiShouZhengMingHao() {
		return huiShouZhengMingHao;
	}

	/**
	 * huiShouZhengMingHao.
	 *
	 * @param   huiShouZhengMingHao    the huiShouZhengMingHao to set
	 * @since   JDK 1.6
	 */
	public void setHuiShouZhengMingHao(String huiShouZhengMingHao) {
		this.huiShouZhengMingHao = huiShouZhengMingHao;
	}

	/**
	 * cheZhu.
	 *
	 * @return  the cheZhu
	 * @since   JDK 1.6
	 */
	public String getCheZhu() {
		return cheZhu;
	}

	/**
	 * cheZhu.
	 *
	 * @param   cheZhu    the cheZhu to set
	 * @since   JDK 1.6
	 */
	public void setCheZhu(String cheZhu) {
		this.cheZhu = cheZhu;
	}

	/**
	 * cheZhuShenFenZheng.
	 *
	 * @return  the cheZhuShenFenZheng
	 * @since   JDK 1.6
	 */
	public String getCheZhuShenFenZheng() {
		return cheZhuShenFenZheng;
	}

	/**
	 * cheZhuShenFenZheng.
	 *
	 * @param   cheZhuShenFenZheng    the cheZhuShenFenZheng to set
	 * @since   JDK 1.6
	 */
	public void setCheZhuShenFenZheng(String cheZhuShenFenZheng) {
		this.cheZhuShenFenZheng = cheZhuShenFenZheng;
	}

	/**
	 * zuZhiJiGouDaiMa.
	 *
	 * @return  the zuZhiJiGouDaiMa
	 * @since   JDK 1.6
	 */
	public String getZuZhiJiGouDaiMa() {
		return zuZhiJiGouDaiMa;
	}

	/**
	 * zuZhiJiGouDaiMa.
	 *
	 * @param   zuZhiJiGouDaiMa    the zuZhiJiGouDaiMa to set
	 * @since   JDK 1.6
	 */
	public void setZuZhiJiGouDaiMa(String zuZhiJiGouDaiMa) {
		this.zuZhiJiGouDaiMa = zuZhiJiGouDaiMa;
	}

	/**
	 * jiGouLeiXing.
	 *
	 * @return  the jiGouLeiXing
	 * @since   JDK 1.6
	 */
	public String getJiGouLeiXing() {
		return jiGouLeiXing;
	}

	/**
	 * jiGouLeiXing.
	 *
	 * @param   jiGouLeiXing    the jiGouLeiXing to set
	 * @since   JDK 1.6
	 */
	public void setJiGouLeiXing(String jiGouLeiXing) {
		this.jiGouLeiXing = jiGouLeiXing;
	}

	/**
	 * queRenShuBianHao.
	 *
	 * @return  the queRenShuBianHao
	 * @since   JDK 1.6
	 */
	public String getQueRenShuBianHao() {
		return queRenShuBianHao;
	}

	/**
	 * queRenShuBianHao.
	 *
	 * @param   queRenShuBianHao    the queRenShuBianHao to set
	 * @since   JDK 1.6
	 */
	public void setQueRenShuBianHao(String queRenShuBianHao) {
		this.queRenShuBianHao = queRenShuBianHao;
	}

	/**
	 * siRen.
	 *
	 * @return  the siRen
	 * @since   JDK 1.6
	 */
	public boolean isSiRen() {
		return siRen;
	}

	/**
	 * siRen.
	 *
	 * @param   siRen    the siRen to set
	 * @since   JDK 1.6
	 */
	public void setSiRen(boolean siRen) {
		this.siRen = siRen;
	}

	/**
	 * jingBanRen.
	 *
	 * @return  the jingBanRen
	 * @since   JDK 1.6
	 */
	public String getJingBanRen() {
		return jingBanRen;
	}

	/**
	 * jingBanRen.
	 *
	 * @param   jingBanRen    the jingBanRen to set
	 * @since   JDK 1.6
	 */
	public void setJingBanRen(String jingBanRen) {
		this.jingBanRen = jingBanRen;
	}

	/**
	 * jingBanRenDH.
	 *
	 * @return  the jingBanRenDH
	 * @since   JDK 1.6
	 */
	public String getJingBanRenDH() {
		return jingBanRenDH;
	}

	/**
	 * jingBanRenDH.
	 *
	 * @param   jingBanRenDH    the jingBanRenDH to set
	 * @since   JDK 1.6
	 */
	public void setJingBanRenDH(String jingBanRenDH) {
		this.jingBanRenDH = jingBanRenDH;
	}

	/**
	 * jingBanRenSFZ.
	 *
	 * @return  the jingBanRenSFZ
	 * @since   JDK 1.6
	 */
	public String getJingBanRenSFZ() {
		return jingBanRenSFZ;
	}

	/**
	 * jingBanRenSFZ.
	 *
	 * @param   jingBanRenSFZ    the jingBanRenSFZ to set
	 * @since   JDK 1.6
	 */
	public void setJingBanRenSFZ(String jingBanRenSFZ) {
		this.jingBanRenSFZ = jingBanRenSFZ;
	}

	/**
	 * lianXiDiZi.
	 *
	 * @return  the lianXiDiZi
	 * @since   JDK 1.6
	 */
	public String getLianXiDiZi() {
		return lianXiDiZi;
	}

	/**
	 * lianXiDiZi.
	 *
	 * @param   lianXiDiZi    the lianXiDiZi to set
	 * @since   JDK 1.6
	 */
	public void setLianXiDiZi(String lianXiDiZi) {
		this.lianXiDiZi = lianXiDiZi;
	}

	/**
	 * youZhengBianMa.
	 *
	 * @return  the youZhengBianMa
	 * @since   JDK 1.6
	 */
	public String getYouZhengBianMa() {
		return youZhengBianMa;
	}

	/**
	 * youZhengBianMa.
	 *
	 * @param   youZhengBianMa    the youZhengBianMa to set
	 * @since   JDK 1.6
	 */
	public void setYouZhengBianMa(String youZhengBianMa) {
		this.youZhengBianMa = youZhengBianMa;
	}

	/**
	 * TODO 输出报废回收接口返回的对象信息.
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		Field[] fields = RecycleInfoEntity.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				sb.append(field.getName()).append(":").append(field.get(this)).append("\n");
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} 
		return sb.toString();
	}
	
	
	
	
}
