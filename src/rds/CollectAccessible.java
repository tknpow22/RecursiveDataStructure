package rds;

public abstract class CollectAccessible<T extends IRdsContent> {

	private Rds<T> rdsRoot;

	public CollectAccessible(Rds<T> rdsRoot) {
		this.rdsRoot = rdsRoot;
	}

	public Rds<T> collect() {
		return this.collectInner(this.rdsRoot, new Rds<T>());
	}

	private Rds<T> collectChild(Rds<T> rds) {
		if (rds.getItem().isLeaf()) {
			return this.isAccessible(rds.getItem()) ? rds : null;
		} else {
			return this.collectInner(rds,  null);
		}
	}

	private Rds<T> collectInner(Rds<T> rdsParent, Rds<T> valueDefault) {
		Rds<T> rdsRoot = new Rds<>(rdsParent.getItem());
		for (Rds<T> crds : rdsParent.getChildren()) {
			Rds<T> acsRds = this.collectChild(crds);
			if (acsRds != null) {
				rdsRoot.getChildren().add(acsRds);
			}
		}
		return (rdsRoot.getChildren().size() == 0) ? valueDefault : rdsRoot;
	}

	protected abstract boolean isAccessible(T item);
}
