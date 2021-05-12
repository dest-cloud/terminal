package cloud.dest.terminal.workspace;

import cloud.dest.terminal.utils.ParseYAMLFile;

import java.io.IOException;

public class WorkspaceServiceImpl implements WorkspaceService {

    @Override
    public Workspace loadWorkspace(String path) {
        Workspace workspace = null;
        try {
            workspace = ParseYAMLFile.readWorkspace(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workspace;
    }
}
