package brere.nat.uploader.model;

import java.util.List;

public class PagingObject<T> {

	private long total;
	private List<T> data;
	
	public PagingObject(final long total, final List<T> data) {
		super();
		this.total = total;
		this.data = data;
	}
	
	public PagingObject() {
		super();
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
}
