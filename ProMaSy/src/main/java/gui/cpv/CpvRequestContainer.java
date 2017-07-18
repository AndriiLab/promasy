package gui.cpv;

public class CpvRequestContainer {
    private String cpvRequest;
    private int depth; // 0 - exact lvl, -1 - upper level, 1 - lower level

    public CpvRequestContainer(String cpvRequest, int depth) {
        this.cpvRequest = cpvRequest;
        this.depth = depth;
    }
	public String getCpvRequest() {
		return cpvRequest;
	}

    public int getDepth() {
        return depth;
    }
}
