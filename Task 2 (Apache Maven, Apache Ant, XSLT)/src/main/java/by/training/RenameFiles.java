package by.training;

import java.io.File;
import java.util.Vector;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;

public class RenameFiles extends Task {

    private String          destDir;
    private String          jobId;
    private Vector<FileSet> filesets = new Vector<FileSet>();

    public void setDestDir(final String destDir) {
        this.destDir = destDir;
    }

    public void setJobId(final String jobId) {
        this.jobId = jobId;
    }

    public void addFileset(final FileSet fileset) {
        filesets.add(fileset);
    }

    @Override
    public void execute() {
        File folder = new File(destDir);
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                renameFile(fileEntry);
            }
        }
    }

    private void listFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                System.out.println(fileEntry.getName());
            }
        }
    }

    private void renameFile(final File file) {
        String foundLocation = null;

        for (FileSet fileSet : filesets) {
            DirectoryScanner directoryScanner = fileSet.getDirectoryScanner(getProject());
            String[] includedFiles = directoryScanner.getIncludedFiles();
            for (String includedFile : includedFiles) {
                String filename = includedFile.replace('\\', '/');
                filename = filename.substring(filename.lastIndexOf("/") + 1);

                if (foundLocation == null && file.equals(filename)) {
                    File base = directoryScanner.getBasedir();
                    File found = new File(base, includedFile);
                    foundLocation = found.getAbsolutePath();
                }

                String target = file.getPath();
                String[] tokens = target.split("\\.(?=[^\\.]+$)");
                File newFile = new File(tokens[0] + "-" + jobId + "." + tokens[1]);
                file.renameTo(newFile);
            }
        }
    }

    protected void validate() {
        if (filesets.size() < 1) {
            throw new BuildException("Fileset is not set");
        }
    }

}
