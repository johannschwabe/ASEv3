const fs = require("fs");

const template_file = "./reports/template.README.md";
const out_file = "./README.md";

const coverage = require("./coverage/coverage-summary.json");
const jest_report = require("./coverage/test-summary.json");

class Report {
  constructor(template) {
    this.content = template;
  }

  insert(identifier, value) {
    this.content = this.content.replace(`<%${identifier}%>`, value);
  }

  insertPercentageBatch(identifier, name, percentage) {
    this.insert(identifier, Report.percentageBatch(name, percentage));
  }

  insertBatch(identifier, name, value, color) {
    this.insert(identifier, Report.createNameBatch(name, value, color));
  }

  save(output_file_path) {
    fs.writeFile(output_file_path, this.content, "utf8", (err) => {
      if (err) { throw err; }
    });
  }

  static percentageBatch(name, percentage) {
    const colors = {
      0: "red",
      50: "orange",
      65: "yellow",
      75: "yellowgreen",
      90: "green",
      100: "brightgreen",
    };
    const color = colors[Object.keys(colors).find((key) => percentage <= key)];
    return `https://img.shields.io/badge/${name}-${percentage}-${color}`;
  }

  static createNameBatch(name, value, color) {
    return `https://img.shields.io/badge/${name}-${value}-${color}`;
  }
}

fs.readFile(template_file, "utf8", (err, input_data) => {
  if (err) {
    throw err;
  }
  const report = new Report(input_data);

  // Creating batches
  report.insertPercentageBatch("batch.linecoverage", "LineCoverage", coverage.total.lines.pct);
  report.insertPercentageBatch("batch.functioncoverage", "FunctionsCoverage", coverage.total.functions.pct);
  report.insertPercentageBatch("batch.statementcoverage", "StatementCoverage", coverage.total.statements.pct);
  report.insertPercentageBatch("batch.batchcoverage", "BranchCoverage", coverage.total.branches.pct);

  const tests_passing = jest_report.success;
  if (tests_passing) {
    report.insertBatch("batch.testspassing", "Tests", "Successfull", "brightgreen");
  } else {
    report.insertBatch("batch.testspassing", "Tests", "Failing", "red");
  }
  report.insertBatch("batch.ntests", "Total", jest_report.numTotalTests, "lightgrey");
  report.insertBatch("batch.nsucceeding", "Passing", jest_report.numPassedTests, "brightgreen");
  report.insertBatch("batch.nfailing", "Failing", jest_report.numFailedTests, "red");

  const failed_test_files = jest_report.testResults.filter((test_file) => test_file.status !== "passed");
  let failed_report = "";
  failed_test_files.forEach((failed_test_file) => {
    failed_report += `### ${failed_test_file.name}\n\n`;
    const tests = failed_test_file.assertionResults;
    if (tests.length > 0) {
      const test_titles = tests[0].ancestorTitles;
      if (test_titles.length > 0) {
        failed_report += `#### ${test_titles[0]}\n\n`;
      }
      tests.forEach((test) => {
        if (test.status !== "passed") {
          failed_report += `- (${test.status}) - **${test.title}.**\n`;
          failed_report += `\`\`\`javascript\n${test.failureMessages.join(", ")}\n\`\`\`\n\n`;
        }
      });
    }
  });
  if (failed_report) {
    report.insert("tests.failed_report", failed_report);
  } else {
    report.insert("tests.failed_report", "No failed tests");
  }

  report.save(out_file);
});
