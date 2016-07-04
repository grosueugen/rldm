package com.gt.rldm.hw6;

public class PatronFunction {
	private final int n;
	
	private final int instigatorPosition;
	
	private final int peacemakerPosition;
	
	public PatronFunction(int n, int instigator, int peacemaker) {
		this.n = n;
		this.instigatorPosition = instigator;
		this.peacemakerPosition = peacemaker;
		if (instigatorPosition < 0 || instigatorPosition > n-1)
			throw new IllegalArgumentException("instigator position wrong. n=" + n + "pos: " + instigatorPosition);
		if (peacemakerPosition < 0 || peacemakerPosition > n-1)
			throw new IllegalArgumentException("peacemaker position wrong. n=" + n + "pos: " + peacemakerPosition);		
	}
	
	/**
	 * <li>Computes: f(x) = y 
	 * <li> If n = 4, then an x = {true, false, true, false}, which means:
	 * patron 1 in set, patron 2 not in set, patron 3 in set, patron 4 not in
	 * set
	 * <li>If peacemaker is in, return NO_FIGHT
	 * <li>Else if instigator is in, return FIGHT
	 * <li>Else return NO_FIGHT 
	 * 
	 * @param x
	 *            an episode
	 * @return FIGHT/NO_FIGHT
	 */
	public PatronResult evaluate(int[] x) {
		if (x.length != n)
			throw new IllegalArgumentException("episode has wrong size. lenght vs n:" + x.length + " vs " + n);
		boolean instigatorIn = (x[instigatorPosition] == 1);
		boolean peacemakerIn = (x[peacemakerPosition] == 1);
		if (peacemakerIn) return PatronResult.NO_FIGHT;
		else if (instigatorIn) return PatronResult.FIGHT;
		else return PatronResult.NO_FIGHT;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + instigatorPosition;
		result = prime * result + n;
		result = prime * result + peacemakerPosition;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PatronFunction other = (PatronFunction) obj;
		if (instigatorPosition != other.instigatorPosition)
			return false;
		if (n != other.n)
			return false;
		if (peacemakerPosition != other.peacemakerPosition)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PatronFunction [n=" + n + ", instigatorPosition=" + instigatorPosition + ", peacemakerPosition="
				+ peacemakerPosition + "]";
	}

}
