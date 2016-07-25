package com.spark_web.domain;

public class Pair<S, C> {
	private S slot;
	private C count;

	public Pair(S slot, C count) {
		this.setSlot(slot);
		this.setCount(count);
	}

	public S getSlot() {
		return slot;
	}

	public void setSlot(S slot) {
		this.slot = slot;
	}

	public C getCount() {
		return count;
	}

	public void setCount(C count) {
		this.count = count;
	}

	
}