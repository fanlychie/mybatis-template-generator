package org.fanlychie.mybatis.schema;

/**
 * 输出模型
 * 
 * @author fanlychie
 */
public class Output {

	private Item entity;

	private Item dao;

	private Item daoImpl;

	private Item mapperXml;

	private Item service;

	private Item serviceImpl;

	public Item getEntity() {
		return entity;
	}

	public void setEntity(Item entity) {
		this.entity = entity;
	}

	public Item getDao() {
		return dao;
	}

	public void setDao(Item dao) {
		this.dao = dao;
	}

	public Item getDaoImpl() {
		return daoImpl;
	}

	public void setDaoImpl(Item daoImpl) {
		this.daoImpl = daoImpl;
	}

	public Item getMapperXml() {
		return mapperXml;
	}

	public void setMapperXml(Item mapperXml) {
		this.mapperXml = mapperXml;
	}

	public Item getService() {
		return service;
	}

	public void setService(Item service) {
		this.service = service;
	}

	public Item getServiceImpl() {
		return serviceImpl;
	}

	public void setServiceImpl(Item serviceImpl) {
		this.serviceImpl = serviceImpl;
	}
	
	public static class Item {
		
		private String folder;
		
		private String packname;
		
		public Item(String folder, String packname) {
			this.folder = folder;
			this.packname = packname;
		}

		public String getPackname() {
			return packname;
		}

		public void setPackname(String packname) {
			this.packname = packname;
		}

		public void setFolder(String folder) {
			this.folder = folder;
		}

		@Override
		public String toString() {
			return swapSeparator(folder) + swapSeparator(packname);
		}
		
	}
	
	private static String swapSeparator(String source) {
		if (source.contains(".")) {
			source = source.replace(".", "/");
		}
		else if (source.contains("\\")) {
			source = source.replaceAll("\\\\", "/");
		}
		if (!source.endsWith("/")) {
			source += "/";
		}
		return source;
	}

}