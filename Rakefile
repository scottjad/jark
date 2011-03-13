require 'rake/testtask'
require 'fileutils'

task :default => ['test']

GEM_NAME = "danmayer-resume"

desc "run sintra server locally"
task :run do
  exec "ruby resume.rb"
end

begin
  require 'jeweler'
  Jeweler::Tasks.new do |gemspec|
    gemspec.name = GEM_NAME
    gemspec.summary = "Resume gem"
    gemspec.description = "A gem for Dan Mayer's resume"
    gemspec.email = "dan@mayerdan.com"
    gemspec.homepage = "http://github.com/icylisper/resume"
    gemspec.authors = ["Isaac Praveen"]
    gemspec.executables = [GEM_NAME]
    gemspec.add_development_dependency "jeweler"
    gemspec.add_dependency "main"
    gemspec.add_dependency "maruku"
    gemspec.add_dependency "sinatra", '~> 1.0'
    gemspec.add_dependency "erubis"
    gemspec.add_dependency 'less'
    gemspec.add_dependency 'launchy'
    gemspec.add_dependency 'rdiscount'
  end
  Jeweler::GemcutterTasks.new
rescue LoadError
  puts "Jeweler not available. Install it with: gem install jeweler"
end

task :build => [:generate_exe]

desc "generate the gem executable"
task :generate_exe do
  puts "making bin folder if it doesn't exist"
  bin_folder = File.join(File.dirname(__FILE__), 'bin')
  FileUtils.mkdir_p(bin_folder) unless File.exists?(bin_folder)
  puts "copying executable from template"
  `cp lib/resume_exe bin/#{GEM_NAME}`
  puts "giving new file chmod +x"
  `chmod o+x bin/#{GEM_NAME}`
end

desc "run all tests"
task :test do
  Rake::Task['test:rack'].invoke
  Rake::Task['test:unit'].invoke
end

namespace :test do
  desc "run rack tests"
  Rake::TestTask.new(:rack) do |t|
    t.libs << "test"
    t.pattern = "test/rack/**/*_test.rb"
    t.verbose = true
  end

  desc "run unit tests"
  Rake::TestTask.new(:unit) do |t|
    t.libs << "test"
    t.pattern = "test/unit/**/*_test.rb"
    t.verbose = true
  end
end

desc "render github index page, which can be displayed at user.github.com"
task :render_for_github do	
    require File.join(File.dirname(__FILE__), 'lib', 'resume_gem')
    resume = Resume.new('resume.yml')
    resume.write_html_and_css_to_disk('./')
end

namespace :heroku do

  desc "create a heroku project for your resume"
  task :create do
    unless ENV.include?("name")	
      raise "usage: rake heroku:create name=PROJECT_NAME # example danmayer-resume\n" 
    end
    project_name = ENV['name']
    puts "creating heroku project #{project_name}"
    puts `heroku create #{project_name}`
  end

end

namespace :deploy do
  desc "Deploy to Heroku."
  task :heroku do
    `git push heroku master`
  end

  desc "Deploy to Github pages."
  task :github => [:render_for_github] do
    # this assumes you have made a remote called github
    # `git remote add github git@github.com:username/username.github.com.git`
    # this should push your resume to http://username.github.com
    `git push github master`
  end
end

namespace :github do
  desc "render github index page, which can be displayed at user.github.com"
  task :render_pages do	
    require File.join(File.dirname(__FILE__), 'lib', 'resume_gem')
    resume = Resume.new('resume.yml')
    puts "writing resume github index files to disk"
    resume.write_html_and_css_to_disk('./')
  end
end
